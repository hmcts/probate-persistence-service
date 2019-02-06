package uk.gov.hmcts.probate.services.persistence.repository;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext)
    {
        System.setProperty("h2.customDataTypesHandler", TestJsonDataTypeHandler.class.getName());
        System.setProperty("h2.javaObjectSerializer", TestJavaObjectSerializer.class.getName());
    }
}