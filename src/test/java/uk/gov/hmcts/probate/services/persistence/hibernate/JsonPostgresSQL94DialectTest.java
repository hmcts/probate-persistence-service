package uk.gov.hmcts.probate.services.persistence.hibernate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Types;
import org.junit.Test;

public class JsonPostgresSQL94DialectTest {

  @Test
  public void shouldRegisterJsonbType() {
    JsonPostgresSQL94Dialect jsonPostgresSQL94Dialect = new JsonPostgresSQL94Dialect();
    assertThat(jsonPostgresSQL94Dialect.getTypeName(Types.JAVA_OBJECT), is(equalTo("jsonb")));
  }
}
