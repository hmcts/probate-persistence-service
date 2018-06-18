package uk.gov.hmcts.probate.services.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import org.h2.api.JavaObjectSerializer;

public class TestJavaObjectSerializer implements JavaObjectSerializer {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(Object obj) throws Exception {
    if (obj instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) obj;
      return objectMapper.writeValueAsBytes(objectNode);
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(out);
    os.writeObject(obj);
    return out.toByteArray();
  }

  @Override
  public Object deserialize(byte[] bytes) throws Exception {
    return objectMapper.readValue(bytes, ObjectNode.class);
  }
}
