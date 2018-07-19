package uk.gov.hmcts.probate.services.persistence.config;

import org.ff4j.FF4j;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnClass({FF4j.class})
@ComponentScan(value = {"org.ff4j.jmx", "org.ff4j.spring.boot.web.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring"})
public class FF4jConfiguration {

    @Value("${ff4j.webapi.authentication}")
    private boolean authentication = false;

    @Value("${ff4j.webapi.authorization}")
    private boolean authorization = false;

    @Bean
    public FF4j getFF4j() {
        return new FF4j("ff4j.xml").autoCreate();
    }

    @Bean
    @Primary
    public FF4jDispatcherServlet getFF4JServlet() {
        FF4jDispatcherServlet ds = new FF4jDispatcherServlet();
        ds.setFf4j(getFF4j());
        return ds;
    }

    @Bean
    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();

        apiConfig.setWebContext("/api");
        apiConfig.setFF4j(getFF4j());
        return apiConfig;
    }

}