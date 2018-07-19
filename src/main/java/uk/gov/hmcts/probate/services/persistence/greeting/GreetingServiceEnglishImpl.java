package uk.gov.hmcts.probate.services.persistence.greeting;

import org.springframework.stereotype.Component;

@Component("english")
public class GreetingServiceEnglishImpl implements GreetingService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
