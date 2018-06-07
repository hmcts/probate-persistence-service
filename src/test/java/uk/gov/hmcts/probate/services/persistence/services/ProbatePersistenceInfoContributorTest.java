package uk.gov.hmcts.probate.services.persistence.services;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.springframework.boot.actuate.info.Info;
import org.springframework.test.util.ReflectionTestUtils;

public class ProbatePersistenceInfoContributorTest {

    private final static String VALID_COMMIT_ID = "2921eb6292eab0bd9ce8a42b44cf32fe9f4b0069";
    private final static String VALID_COMMIT_TIME = "2018-05-23T13:59+0000";
    
    private final static String EMPTY_VALUE = "";
    private ProbatePersistenceInfoContributor infoContributor = new ProbatePersistenceInfoContributor();

    @Test
    public void testValidGitCommitValues() {

        try {
            ReflectionTestUtils.setField(infoContributor, "commitId", VALID_COMMIT_ID);
            ReflectionTestUtils.setField(infoContributor, "commitTime", VALID_COMMIT_TIME);
            
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);

            @SuppressWarnings("unchecked")
			Map<String, Map<String, String>> gitMap = (Map<String, Map<String, String>>) builder.build().getDetails().get("git");
            Map<String, String> commitMap = (Map<String, String>) gitMap.get("commit");
            assertEquals("Checking valid git commit id", VALID_COMMIT_ID, commitMap.get("id"));            
            assertEquals("Checking valid git commit date", VALID_COMMIT_TIME, commitMap.get("time"));

        } catch (Exception e){
            assert(false);
        }
    }
    
    @Test
    public void testNullGitCommitValues() {
    	
        try {
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);
            
            @SuppressWarnings("unchecked")
			Map<String, Map<String, String>> gitMap = (Map<String, Map<String, String>>) builder.build().getDetails().get("git");
            Map<String, String> commitMap = (Map<String, String>) gitMap.get("commit");
            assertEquals("Checking empty git commit id", EMPTY_VALUE, commitMap.get("id"));            
            assertEquals("Checking empty git commit date", EMPTY_VALUE, commitMap.get("time"));            

        } catch (Exception e){
            assert(false);
        }
    }  

    @Test
    public void testPartialGitCommitValues() {
    	
        try {
            ReflectionTestUtils.setField(infoContributor, "commitId", EMPTY_VALUE);
            ReflectionTestUtils.setField(infoContributor, "commitTime", VALID_COMMIT_TIME);
            
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);
            
            @SuppressWarnings("unchecked")
			Map<String, Map<String, String>> gitMap = (Map<String, Map<String, String>>) builder.build().getDetails().get("git");
            Map<String, String> commitMap = (Map<String, String>) gitMap.get("commit");
            assertEquals("Checking empty git commit id", EMPTY_VALUE, commitMap.get("id"));   
            assertEquals("Checking valid git commit date", VALID_COMMIT_TIME, commitMap.get("time"));            

        } catch (Exception e){
            assert(false);
        }
    }
      
}
