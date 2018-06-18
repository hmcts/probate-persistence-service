package uk.gov.hmcts.probate.services.persistence.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
@EntityScan(basePackages = {"uk.gov.hmcts.probate.services.persistence.model"})
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class RepositoryTestConfiguration extends RepositoryRestConfigurerAdapter {

}
