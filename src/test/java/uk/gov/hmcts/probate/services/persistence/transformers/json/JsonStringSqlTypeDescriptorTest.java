package uk.gov.hmcts.probate.services.persistence.transformers.json;

import static org.mockito.Mockito.verify;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonStringSqlTypeDescriptorTest {

  private JsonStringSqlTypeDescriptor jsonStringSqlTypeDescriptor;

  @Mock
  private StringTypeDescriptor javaTypeDescriptor;

  @Mock
  private PreparedStatement preparedStatement;

  @Mock
  private CallableStatement callableStatement;

  @Mock
  private WrapperOptions wrapperOptions;

  @Before
  public void setUp() {
    jsonStringSqlTypeDescriptor = new JsonStringSqlTypeDescriptor();
  }

  @Test
  public void shouldGetBinderAndBindWithPreparedStatement() throws Exception {
    ValueBinder valueBinder = jsonStringSqlTypeDescriptor.getBinder(javaTypeDescriptor);
    valueBinder.bind(preparedStatement, "Test", 1, wrapperOptions);
    verify(javaTypeDescriptor).unwrap("Test", String.class, wrapperOptions);
  }

  @Test
  public void shouldGetBinderAndBindWithCallableStatement() throws Exception {
    ValueBinder valueBinder = jsonStringSqlTypeDescriptor.getBinder(javaTypeDescriptor);
    valueBinder.bind(callableStatement, "Test", 1, wrapperOptions);
    verify(javaTypeDescriptor).unwrap("Test", String.class, wrapperOptions);
  }

}