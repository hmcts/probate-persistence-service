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
import uk.gov.hmcts.probate.services.persistence.model.Submission;

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

    private List<String> registries = new ArrayList<>();
    
    public List<String> getRegistries() {
        return registries;
    }

    @Bean
    public Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers() {
        return registries
                .stream()
                .collect(Collectors.toMap(s -> s, s -> new PostgresSequenceMaxValueIncrementer(dataSource, s + "_sequence")));
    }
}
