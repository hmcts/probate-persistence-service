package uk.gov.hmcts.probate.services.persistence.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class ProbatePersistenceInfoContributor implements InfoContributor {

    @Value("${git.commit.id}")
    private String commitId;
   
    @Value("${git.commit.time}")
    private String commitTime;
    
    private static final String INFO_GROUP_NAME_GIT = "git";
    private static final String INFO_GROUP_NAME_COMMIT = "commit";
    private static final String GIT_COMMIT_ID_KEY = "id";
    private static final String GIT_COMMIT_TIME_KEY = "time";
    
	@Override
	public void contribute(Info.Builder builder) {
		
		HashMap<String, String> commitMap = new HashMap<String, String>();
		commitMap.put(GIT_COMMIT_TIME_KEY, Objects.toString(commitTime, ""));
		commitMap.put(GIT_COMMIT_ID_KEY, Objects.toString(commitId, ""));
		  
    	HashMap<String, Map<String, String>> gitMap = new HashMap<String, Map<String, String>>();	
    	gitMap.put(INFO_GROUP_NAME_COMMIT, commitMap );  
    	
    	builder.withDetail(INFO_GROUP_NAME_GIT, gitMap );
    	
	}

}