package uk.gov.hmcts.probate.services.persistence.model;

import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.probate.services.persistence.TestUtils.getJsonFromFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

public class SubmissionTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  private Submission submission;

  @Before
  public void setUp() throws Exception {
    String json = getJsonFromFile("submitDataSerializationTest.json");
    submission = objectMapper.readValue(json, Submission.class);
  }

  @Test
  public void shouldBeConsistent() throws IllegalAccessException {
    byte[] serialized1 = serialize(submission);
    byte[] serialized2 = serialize(submission);

    Object deserialized1 = deserialize(serialized1);
    Object deserialized2 = deserialize(serialized2);

    assertThat(getId((Submission) deserialized1), is(equalTo(getId((Submission) deserialized2))));
    assertThat(getSubmitData((Submission) deserialized1),
        is(equalTo(getSubmitData((Submission) deserialized2))));
  }

  @Test
  public void shouldMatchExpectedInstanceWhenSerializedAndDeserialized()
      throws IllegalAccessException {
    byte[] serialized = serialize(submission);
    Object deserialized = deserialize(serialized);

    assertThat(deserialized, instanceOf(Submission.class));
    assertThat(getId(submission), is(equalTo(getId((Submission) deserialized))));
    assertThat(getSubmitData(submission), is(equalTo(getSubmitData((Submission) deserialized))));
  }

  private Long getId(Submission submission) throws IllegalAccessException {
    return (Long) FieldUtils.readField(submission, "id", true);
  }

  private Map getSubmitData(Submission submission) throws IllegalAccessException {
    return (Map) FieldUtils.readField(submission, "submitData", true);
  }
}
