package uk.gov.hmcts.probate.services.persistence.transformers.json;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hibernate.usertype.DynamicParameterizedType.PARAMETER_TYPE;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Properties;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.usertype.DynamicParameterizedType.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonTypeDescriptorTest {

  private JsonTypeDescriptor jsonTypeDescriptor;

  @Mock
  private ParameterType parameterType;

  @Mock
  private WrapperOptions wrapperOptions;

  @Before
  public void setUp() {
    jsonTypeDescriptor = new JsonTypeDescriptor();
    when(parameterType.getReturnedClass()).thenReturn(HashMap.class);
    Properties properties = new Properties();
    properties.put(PARAMETER_TYPE, parameterType);
    jsonTypeDescriptor.setParameterValues(properties);
  }

  @Test
  public void shouldReturnTrueWhenObjectsAreEqual() {
    assertThat(jsonTypeDescriptor.areEqual("Test", "Test"), is(equalTo(true)));
  }

  @Test
  public void shouldConvertFromString() {
    HashMap<String, Integer> map = new HashMap<>();
    map.put("test", 1);
    assertThat(jsonTypeDescriptor.fromString("{\"test\" : 1}"), is(equalTo(map)));
  }

  @Test
  public void shouldUnwrapNull() {
    assertThat(jsonTypeDescriptor.unwrap(null, String.class, wrapperOptions), is(nullValue()));
  }

  @Test
  public void shouldUnwrapString() {
    assertThat(jsonTypeDescriptor.unwrap("test", String.class, wrapperOptions), is(notNullValue()));
  }

  @Test
  public void shouldWrap() {
    HashMap<String, Integer> map = new HashMap<>();
    map.put("test", 1);
    assertThat(jsonTypeDescriptor.wrap("{\"test\" : 1}", wrapperOptions), is(map));
  }
}
