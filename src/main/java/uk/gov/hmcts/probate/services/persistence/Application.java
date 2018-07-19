package uk.gov.hmcts.probate.services.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
