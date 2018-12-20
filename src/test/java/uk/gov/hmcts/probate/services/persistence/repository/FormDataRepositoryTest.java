package uk.gov.hmcts.probate.services.persistence.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.probate.services.persistence.TestUtils.getJsonFromFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    "spring.jpa.hibernate.dialect=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto: update",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
public class FormDataRepositoryTest {

  {
    System.setProperty("h2.customDataTypesHandler", TestJsonDataTypeHandler.class.getName());
    System.setProperty("h2.javaObjectSerializer", TestJavaObjectSerializer.class.getName());
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private FormdataRepository formdataRepository;

  @Before
  public void setUp() throws Exception {
    mockMvc.perform(post("/formdata/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(getJsonFromFile("formData.json")))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldGetFormData() throws Exception {
    mockMvc.perform(get("/formdata/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldFindBySubmissionReference() throws Exception {
    mockMvc.perform(get("/formdata/search/findBySubmissionReference?submissionReference=123"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldFindById() throws Exception {
    mockMvc.perform(get("/formdata/search/findById?id=1"))
        .andExpect(status().isOk());
  }
}
