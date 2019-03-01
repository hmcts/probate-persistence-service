package uk.gov.hmcts.probate.services.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

  public TestUtils() {
  }

  public static String getJsonFromFile(String fileName) throws Exception {
    try {
      return new String(Files.readAllBytes(Paths
        .get(TestUtils.class.getClassLoader().getResource(fileName).toURI())));
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public JsonNode getJsonNodeFromFile(String fileName) {
    try {
      return new ObjectMapper().readTree(getJsonFromFile(fileName));
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
