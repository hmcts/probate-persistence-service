package uk.gov.hmcts.probate.services.persistence.greeting;

import org.ff4j.aop.Flip;
import org.springframework.stereotype.Component;

@Component
public interface GreetingService {
    @Flip(name = "testFeature", alterBean = "french")
    String sayHello(String name);
}
