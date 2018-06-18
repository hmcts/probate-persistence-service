package uk.gov.hmcts.probate.services.persistence;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

  private TestUtils() {
  }

  public static String getJsonFromFile(String fileName) throws Exception {
    return new String(Files.readAllBytes(Paths
        .get(TestUtils.class.getClassLoader().getResource(fileName).toURI())));
  }
}
