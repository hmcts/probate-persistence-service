package uk.gov.hmcts.probate.services.persistence.transformers.json;

import jdk.nashorn.internal.ir.ObjectNode;
import org.junit.Test;

public class JacksonUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWithInvalidJsonWhenConvertingFromString(){
    JacksonUtil jacksonUtil = new JacksonUtil();
    jacksonUtil.fromString("{random,}", ObjectNode.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWithInvalidObjectWhenConvertingToString(){
    JacksonUtil.toString(new ClassThatJacksonCannotSerialize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWithInvalidJsonWhenConvertingToJsonNode(){
    JacksonUtil.toJsonNode("{random,}");
  }

  private static class ClassThatJacksonCannotSerialize {
    private final ClassThatJacksonCannotSerialize self = this;

    @Override
    public String toString() {
      return self.getClass().getName();
    }
  }
}
