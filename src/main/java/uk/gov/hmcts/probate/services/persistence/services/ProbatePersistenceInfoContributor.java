package uk.gov.hmcts.probate.services.persistence.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class ProbatePersistenceInfoContributor implements InfoContributor {

    @Value("${git.commit.id}")
    private String commitId;
    
    @Value("${git.commit.message.full}")
    private String commitMessage;
   
    @Value("${git.commit.time}")
    private String commitTime;
    
    private static final String INFO_GROUP_NAME = "git";
    private static final String GIT_COMMIT_ID_KEY = "commitId";
    private static final String GIT_COMMIT_MESSAGE_KEY = "commitMessage";
    private static final String GIT_COMMIT_TIME_KEY = "commitTime";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    
	@Override
	public void contribute(Info.Builder builder) {
		
		String commitDateString;
		HashMap<String, String> results = new HashMap<String, String>();	
		
		try {
			commitDateString = dateFormat.format(new Date(Long.parseLong(commitTime) * 1000 ));
		} catch ( NumberFormatException nfe ) {
			commitDateString = null;
		}
	
    	results.put(GIT_COMMIT_ID_KEY, Objects.toString(commitId, ""));
    	results.put(GIT_COMMIT_MESSAGE_KEY, Objects.toString(commitMessage, ""));	
    	results.put(GIT_COMMIT_TIME_KEY, Objects.toString(commitDateString, ""));     		
    	builder.withDetail(INFO_GROUP_NAME, results);
    	
	}

}