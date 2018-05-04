package uk.gov.hmcts.probate.services.persistence.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * GitCommitService adds an extra gitCommitId to the healthcheck
 * end-point. Please note that class relies on the application.
 * properties <code>management.security.enabled</code> to be true
 * to show any health property other than <i>status</i>.
 */
@Component
public class GitCommitService extends AbstractHealthIndicator {

    /**
     * Commit Id found from properties file.
     */
    @Value("${git.commit.id}")
    private String commitId;

    /**
     * Override doHealthCheck to show git commit id during health check end-point.
     * @param builder defined from AbstractHealthIndicator class.
     * @throws Exception if doHealthCheck is unable to return a status.
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Check commit id exists - this doesn't affect the overall status.
        if ( null == commitId || commitId.isEmpty() ) {
            builder.unknown().withDetail("gitCommitId", "");
        } else {
            builder.up().withDetail("gitCommitId", commitId);
        }
    }
}