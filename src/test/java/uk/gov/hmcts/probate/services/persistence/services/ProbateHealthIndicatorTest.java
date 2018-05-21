package uk.gov.hmcts.probate.services.persistence.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.test.util.ReflectionTestUtils;

public class ProbateHealthIndicatorTest {

    private final static String VALID_COMMIT_ID = "2921eb6292eab0bd9ce8a42b44cf32fe9f4b0069";
    private final static String EMPTY_COMMIT_ID = "";
    private ProbateHealthIndicator healthIndicator = new ProbateHealthIndicator();

    @Test
    public void testValidGitCommitIdValue() {

        try {
            ReflectionTestUtils.setField(healthIndicator, "commitId", VALID_COMMIT_ID);
            Health health = healthIndicator.health();
            final String foundCommitId = health.getDetails().get("gitCommitId").toString();

            assertEquals("Checking valid status", Status.UP, health.getStatus());            
            assertEquals("Checking valid git commit id", VALID_COMMIT_ID, foundCommitId);

        } catch (Exception e){
            assert(false);
        }
    }

    /**
     * Test to ensure missing git information is correctly processed.
     */
    @Test
    public void testMissingGitCommitIdValue() {

        try {
            ReflectionTestUtils.setField(healthIndicator, "commitId", EMPTY_COMMIT_ID);
            Health health = healthIndicator.health();
            final String foundCommitId = health.getDetails().get("gitCommitId").toString();

            assertEquals("Checking unknown status", Status.UNKNOWN, health.getStatus());
            assertEquals("Checking missing git commit id", EMPTY_COMMIT_ID, foundCommitId);

        } catch (Exception e){
            assert(false);
        }
    }
}
