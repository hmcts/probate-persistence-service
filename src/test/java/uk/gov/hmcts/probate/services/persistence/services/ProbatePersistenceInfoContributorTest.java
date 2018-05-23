package uk.gov.hmcts.probate.services.persistence.services;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.springframework.boot.actuate.info.Info;
import org.springframework.test.util.ReflectionTestUtils;

public class ProbatePersistenceInfoContributorTest {

	private final static String GIT_COMMIT_TIME = "1526912708";
    private final static String VALID_COMMIT_ID = "2921eb6292eab0bd9ce8a42b44cf32fe9f4b0069";
    private final static String VALID_COMMIT_MESSAGE = "PRO-1234: Initial build of code."; 
    private final static String VALID_COMMIT_TIME = "Mon, 21 May 2018 15:25:08 BST";
    
    private final static String EMPTY_VALUE = "";
    private ProbatePersistenceInfoContributor infoContributor = new ProbatePersistenceInfoContributor();

    @Test
    public void testValidGitCommitValues() {

        try {
            ReflectionTestUtils.setField(infoContributor, "commitId", VALID_COMMIT_ID);
            ReflectionTestUtils.setField(infoContributor, "commitMessage", VALID_COMMIT_MESSAGE);
            ReflectionTestUtils.setField(infoContributor, "commitTime", GIT_COMMIT_TIME);
            
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);
            
            Map<String, String> gitMap = (Map<String, String>) builder.build().getDetails().get("git");
            assertEquals("Checking valid git commit id", VALID_COMMIT_ID, gitMap.get("commitId"));            
            assertEquals("Checking valid git commit message", VALID_COMMIT_MESSAGE, gitMap.get("commitMessage"));
            assertEquals("Checking valid git commit date", VALID_COMMIT_TIME, gitMap.get("commitTime"));

        } catch (Exception e){
            assert(false);
        }
    }
    
    /**
     * Test to ensure null git commit id information is correctly processed.
     */
    @Test
    public void testNullGitCommitValues() {
    	
        try {
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);
            
            Map<String, String> gitMap = (Map<String, String>) builder.build().getDetails().get("git");
            assertEquals("Checking empty git commit id", EMPTY_VALUE, gitMap.get("commitId"));            
            assertEquals("Checking empty git commit message", EMPTY_VALUE, gitMap.get("commitMessage"));
            assertEquals("Checking empty git commit date", EMPTY_VALUE, gitMap.get("commitTime"));            

        } catch (Exception e){
        	e.printStackTrace();
            assert(false);
        }
    }  

    /**
     * Test to ensure empty git commit id information is correctly processed.
     */
    @Test
    public void testPartialGitCommitValues() {
    	
        try {
            ReflectionTestUtils.setField(infoContributor, "commitId", EMPTY_VALUE);
            ReflectionTestUtils.setField(infoContributor, "commitMessage", VALID_COMMIT_MESSAGE);
            ReflectionTestUtils.setField(infoContributor, "commitTime", GIT_COMMIT_TIME);
            
            Info.Builder builder = new Info.Builder();
            infoContributor.contribute(builder);
            
            Map<String, String> gitMap = (Map<String, String>) builder.build().getDetails().get("git");
            assertEquals("Checking empty git commit id", EMPTY_VALUE, gitMap.get("commitId"));   
            assertEquals("Checking valid git commit message", VALID_COMMIT_MESSAGE, gitMap.get("commitMessage"));
            assertEquals("Checking valid git commit date", VALID_COMMIT_TIME, gitMap.get("commitTime"));            

        } catch (Exception e){
            assert(false);
        }
    }
      
}
