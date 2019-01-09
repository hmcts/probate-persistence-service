package uk.gov.hmcts.probate.services.persistence.transformers.json;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

  @Mock
  private ResultSet resultSet;

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
    valueBinder.bind(callableStatement, "Test", "", wrapperOptions);
    verify(javaTypeDescriptor).unwrap("Test", String.class, wrapperOptions);
  }

  @Test
  public void shouldDoExtractWithIndexAndCallableStatement() throws Exception {
    ValueExtractor<String> extractor = jsonStringSqlTypeDescriptor.getExtractor(javaTypeDescriptor);
    when(callableStatement.getObject(1)).thenReturn("Test");
    extractor.extract(callableStatement, 1, wrapperOptions);
    verify(javaTypeDescriptor).wrap("Test", wrapperOptions);
  }

  @Test
  public void shouldDoExtractWithNameAndCallableStatement() throws Exception {
    ValueExtractor<String> extractor = jsonStringSqlTypeDescriptor.getExtractor(javaTypeDescriptor);
    when(callableStatement.getObject("name")).thenReturn("Test");
    extractor.extract(callableStatement, new String[]{"name"}, wrapperOptions);
    verify(javaTypeDescriptor).wrap("Test", wrapperOptions);
  }

  @Test
  public void shouldDoExtractWithNameAndResultSet() throws Exception {
    ValueExtractor<String> extractor = jsonStringSqlTypeDescriptor.getExtractor(javaTypeDescriptor);
    when(resultSet.getObject("name")).thenReturn("Test");
    extractor.extract(resultSet, "name", wrapperOptions);
    verify(javaTypeDescriptor).wrap("Test", wrapperOptions);
  }
}
