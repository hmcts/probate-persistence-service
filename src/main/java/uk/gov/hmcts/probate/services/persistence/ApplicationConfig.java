package uk.gov.hmcts.probate.services.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;

import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.model.Submission;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;

@EntityScan(basePackages = {"uk.gov.hmcts.probate.services.persistence.model"})
@EnableJpaRepositories(basePackages = {"uk.gov.hmcts.probate.services.persistence.repository"})
@Configuration
@ConfigurationProperties
@PropertySource(value = "git.properties", ignoreResourceNotFound = true)
public class ApplicationConfig  extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Submission.class);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RegistryRepository registryRepository;

    private List<Registry> registries = new ArrayList<>();

    public List<Registry> getRegistries() {
        return registries;
    }

    @Bean
    public Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers() {
        return registries
                .stream()
                .collect(Collectors.toMap(Registry::getId, s -> new PostgresSequenceMaxValueIncrementer(dataSource, s.getId() + "_sequence")));
    }

    @Bean
    public List<Registry> updateRegistries() {
        List<String> registryData = registryRepository.findAll().stream()
                .map(Registry::getId)
                .collect(Collectors.toList());
        List<String> registryNames = registries.stream()
                .map(Registry::getId)
                .collect(Collectors.toList());

        registries.forEach(r -> {
                if (registryData.contains(r.getId())) {
                    registryRepository.updateRatio(r.getRatio(), r.getId());
                } else {
                    registryRepository.save(r);
                }
            });
        registryData.removeAll(registryNames);
        registryData.forEach(r -> registryRepository.delete(registryRepository.findById(r)));
        return registries;
    }
}
