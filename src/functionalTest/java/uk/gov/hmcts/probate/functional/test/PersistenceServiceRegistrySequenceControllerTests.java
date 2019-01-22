package uk.gov.hmcts.probate.functional.test;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class PersistenceServiceRegistrySequenceControllerTests extends IntegrationTestBase {

    @Test
    public void getNextRegistryWithSuccess() {
        validateGetSuccess(1234L);
    }


    @Test
    public void getNextRegistryWithFailure() {
        String invalidRegistry = "dundee";
        String errorMessage = "java.lang.NumberFormatException: For input string: \"" + invalidRegistry;
        validateGetFailure(invalidRegistry, errorMessage);
    }

    private void validateGetSuccess(Long submissionReference) {
        SerenityRest.given().relaxedHTTPSValidation()
                .headers(utils.getHeaders())
                .when().get(persistenceServiceUrl + "/registry/" + submissionReference)
                .then().assertThat().statusCode(200);
    }

    private void validateGetFailure(String registry, String errorMessage) {
        Response response = SerenityRest.given().relaxedHTTPSValidation()
                .headers(utils.getHeaders())
                .when().get(persistenceServiceUrl + "/registry/" + registry)
                .thenReturn();

        response.then().assertThat().statusCode(400)
                .and().body("error", equalTo("Bad Request"))
                .and().body("message", containsString(errorMessage));

    }
}
