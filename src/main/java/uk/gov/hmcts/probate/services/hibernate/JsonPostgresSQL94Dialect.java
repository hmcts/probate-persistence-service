package uk.gov.hmcts.probate.services.persistence.hibernate;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class JsonPostgresSQL94Dialect extends PostgreSQL94Dialect {
    public JsonPostgresSQL94Dialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
