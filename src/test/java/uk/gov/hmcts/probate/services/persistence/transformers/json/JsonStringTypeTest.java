package uk.gov.hmcts.probate.services.persistence.transformers.json;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hibernate.usertype.DynamicParameterizedType.PARAMETER_TYPE;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Properties;
import org.hibernate.usertype.DynamicParameterizedType.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonStringTypeTest {

  private JsonStringType jsonStringType;

  @Mock
  private ParameterType parameterType;

  @Before
  public void setUp() {
    jsonStringType = new JsonStringType();
  }

  @Test
  public void shouldRegisterUnderJavaType() {
    assertThat(jsonStringType.registerUnderJavaType(), is(equalTo(true)));
  }

  @Test
  public void shouldHaveNameOfJson() {
    assertThat(jsonStringType.getName(), is(equalTo("json")));
  }

  @Test
  public void shouldSetParameterValues() {
    when(parameterType.getReturnedClass()).thenReturn(HashMap.class);
    Properties properties = new Properties();
    properties.put(PARAMETER_TYPE, parameterType);
    jsonStringType.setParameterValues(properties);

    verify(parameterType).getReturnedClass();
  }

}