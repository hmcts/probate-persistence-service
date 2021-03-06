package uk.gov.hmcts.probate.functional.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("uk.gov.hmcts.probate.functional.test")
@PropertySource("file:src/functionalTest/resources/application.properties")
public class TestContextConfiguration {
}
