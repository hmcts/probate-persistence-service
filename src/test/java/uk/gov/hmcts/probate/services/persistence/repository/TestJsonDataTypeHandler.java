package uk.gov.hmcts.probate.services.persistence.repository;

import java.sql.Types;
import org.h2.api.CustomDataTypesHandler;
import org.h2.store.DataHandler;
import org.h2.value.DataType;
import org.h2.value.Value;

public class TestJsonDataTypeHandler implements CustomDataTypesHandler {

  private static final String JSONB = "jsonb";

  @Override
  public DataType getDataTypeByName(String name) {
    if (name.equalsIgnoreCase(JSONB)) {
      return createJsonbDataType();
    }
    return null;
  }

  private DataType createJsonbDataType() {
    DataType result = new DataType();
    result.type = Types.JAVA_OBJECT;
    result.name = JSONB;
    result.sqlType = Types.JAVA_OBJECT;
    return result;
  }

  @Override
  public DataType getDataTypeById(int type) {
    return null;
  }

  @Override
  public int getDataTypeOrder(int type) {
    return 0;
  }

  @Override
  public Value convert(Value source, int targetType) {
    return null;
  }

  @Override
  public String getDataTypeClassName(int type) {
    return null;
  }

  @Override
  public int getTypeIdFromClass(Class<?> cls) {
    return 0;
  }

  @Override
  public Value getValue(int type, Object data, DataHandler dataHandler) {
    return null;
  }

  @Override
  public Object getObject(Value value, Class<?> cls) {
    return null;
  }

  @Override
  public boolean supportsAdd(int type) {
    return false;
  }

  @Override
  public int getAddProofType(int type) {
    return 0;
  }

}
