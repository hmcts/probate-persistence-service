package uk.gov.hmcts.probate.functional;

import net.thucydides.junit.spring.SpringIntegration;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.probate.functional.util.TestUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public abstract class IntegrationTestBase {

    @Autowired
    protected TestUtils utils;

    String persistenceServiceUrl;

    @Autowired
    public void persistenceServiceUrl(@Value("${probate.persistence.url}") String persistenceServiceUrl) {
        this.persistenceServiceUrl = persistenceServiceUrl.replaceAll("\'","");
    }

    @Rule
    public SpringIntegration springIntegration;

    IntegrationTestBase() {
        this.springIntegration = new SpringIntegration();
    }
}
