package uk.gov.hmcts.probate.services.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import uk.gov.hmcts.probate.services.persistence.model.Submission;

@SpringBootApplication
@EntityScan(basePackages = {"uk.gov.hmcts.probate.services.persistence.model"})
@EnableJpaRepositories(basePackages = {"uk.gov.hmcts.probate.services.persistence.repository"})

public class Application extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Submission.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
