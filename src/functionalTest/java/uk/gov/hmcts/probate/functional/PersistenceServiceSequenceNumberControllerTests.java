package uk.gov.hmcts.probate.functional;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class PersistenceServiceSequenceNumberControllerTests extends IntegrationTestBase {

    private static final String BIRMINGHAM = "birmingham";
    private static final String OXFORD = "oxford";

    @Test
    public void getNextOxfordWithSuccess() {
        validateGetSuccess(OXFORD);
    }

    @Test
    public void getNextBirminghamWithSuccess() {
        validateGetSuccess(BIRMINGHAM);
    }

    @Test
    public void getNextRegistryWithFailure() {
        String invalidRegistry = "dundee";
        String errorMessage = "Registry not configured: " + invalidRegistry;
        validateGetFailure(invalidRegistry, errorMessage, 404);
    }

    private void validateGetSuccess(String registry) {
        SerenityRest.given()
                .headers(utils.getHeaders())
                .when().get(persistenceServiceUrl + "/sequence-number/" + registry)
                .then().assertThat().statusCode(200);
    }

    private void validateGetFailure(String registry, String errorMessage, Integer statusCode) {
        Response response = SerenityRest.given()
                .headers(utils.getHeaders())
                .when().get(persistenceServiceUrl + "/sequence-number/" + registry)
                .thenReturn();

        response.then().assertThat().statusCode(statusCode)
                .and().body("error", equalTo("Not Found"))
                .and().body("message", equalTo(errorMessage));

    }
}
