package uk.gov.hmcts.probate.services.persistence.services;

import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;

public class GitCommitServiceTest {

    /*
     * Commit ID expected in test.
     */
    private final static String VALID_COMMIT_ID = "2921eb6292eab0bd9ce8a42b44cf32fe9f4b0069";

    /*
     * Commit ID missing in test.
     */
    private final static String EMPTY_COMMIT_ID = "";

    /*
     * Status expected if Commit Id is missing.
     */
    private final static String STATUS_UNKNOWN = "UNKNOWN";

    /*
     * Status expected if Commit Id is present.
     */
    private final static String STATUS_UP = "UP";

    /*
     * GitCommitIdService instance to test.
     */
    private GitCommitService healthIndicator = new GitCommitService();

    /**
     * Test to ensure valid git information is correctly processed.
     */
    @Test
    public void testValidGitCommitIdValue() {

        try {
            // Add a valid commit id to the GitCommitIdService class.
            // This would usually be picked up through properties.
            ReflectionTestUtils.setField(healthIndicator, "commitId", VALID_COMMIT_ID);

            // Instantiate builder and run the health end-point.
            Builder builder = new Builder();
            healthIndicator.doHealthCheck(builder);
            Health health = builder.build();

            // Get status and commit id from GitCommitIdService class.
            final String foundStatus = health.getStatus().toString();
            final String foundCommitId = health.getDetails().get("gitCommitId").toString();

            // Check correct status is found
            assertEquals("Checking valid status", STATUS_UP, foundStatus);

            // Check correct commit id is found
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
            // Ensure commit id is missing...
            ReflectionTestUtils.setField(healthIndicator, "commitId", EMPTY_COMMIT_ID);

            // Instantiate builder and run the health end-point.
            Builder builder = new Builder();
            healthIndicator.doHealthCheck(builder);
            Health health = builder.build();

            // Get status and commit id from GitCommitIdService class.
            final String foundStatus = health.getStatus().toString();
            final String foundCommitId = health.getDetails().get("gitCommitId").toString();

            // Check correct status is found
            assertEquals("Checking unknown status", STATUS_UNKNOWN, foundStatus);

            // Check correct commit id is found
            assertEquals("Checking missing git commit id", EMPTY_COMMIT_ID, foundCommitId);

        } catch (Exception e){
            assert(false);
        }
    }
}
