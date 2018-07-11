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

public class FormdataTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  private Formdata formdata;

  @Before
  public void setUp() throws Exception {
    String json = getJsonFromFile("formDataSerializationTest.json");
    formdata = objectMapper.readValue(json, Formdata.class);
  }

  @Test
  public void shouldBeConsistent() throws IllegalAccessException {
    byte[] serialized1 = serialize(formdata);
    byte[] serialized2 = serialize(formdata);

    Object deserialized1 = deserialize(serialized1);
    Object deserialized2 = deserialize(serialized2);

    assertThat(getId((Formdata) deserialized1), is(equalTo(getId((Formdata) deserialized2))));
    assertThat(getSubmissionReference((Formdata) deserialized1),
        is(equalTo(getSubmissionReference((Formdata) deserialized2))));
    assertThat(getFormData((Formdata) deserialized1),
        is(equalTo(getFormData((Formdata) deserialized2))));
  }

  @Test
  public void shouldMatchExpectedInstanceWhenSerializedAndDeserialized()
      throws IllegalAccessException {
    byte[] serialized = serialize(formdata);
    Object deserialized = deserialize(serialized);

    assertThat(deserialized, instanceOf(Formdata.class));
    assertThat(getId(formdata), is(equalTo(getId((Formdata) deserialized))));
    assertThat(getSubmissionReference(formdata),
        is(equalTo(getSubmissionReference((Formdata) deserialized))));
    assertThat(getFormData(formdata), is(equalTo(getFormData((Formdata) deserialized))));
  }

  private String getId(Formdata formdata) throws IllegalAccessException {
    return (String) FieldUtils.readField(formdata, "id", true);
  }

  private Map getFormData(Formdata formdata) throws IllegalAccessException {
    return (Map) FieldUtils.readField(formdata, "formData", true);
  }

  private Long getSubmissionReference(Formdata formdata) throws IllegalAccessException {
    return (Long) FieldUtils.readField(formdata, "submissionReference", true);
  }
}