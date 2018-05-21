package uk.gov.hmcts.probate.services.persistence.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ProbateHealthIndicator implements HealthIndicator {

    @Value("${git.commit.id}")
    private String commitId;
    
    private static final String GIT_COMMIT_ID_KEY = "gitCommitId";
    private static final String GIT_COMMIT_ID_UNKNOWN_VALUE = "";
    
    @Override
    public Health health() {    	
    	if ( null == commitId || commitId.isEmpty() ) {
            return Health.unknown()
                    .withDetail(GIT_COMMIT_ID_KEY, GIT_COMMIT_ID_UNKNOWN_VALUE)
                .build();
    	} else {
            return Health.up()
                    .withDetail(GIT_COMMIT_ID_KEY, this.commitId)
                .build();
    	}
    }        
}
