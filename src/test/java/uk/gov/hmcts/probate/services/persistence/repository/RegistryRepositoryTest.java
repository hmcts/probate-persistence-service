package uk.gov.hmcts.probate.services.persistence.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
import uk.gov.hmcts.probate.services.persistence.model.Registry;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.jpa.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto: update",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
public class RegistryRepositoryTest {

    {
        System.setProperty("h2.customDataTypesHandler", TestJsonDataTypeHandler.class.getName());
        System.setProperty("h2.javaObjectSerializer", TestJavaObjectSerializer.class.getName());
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegistryRepository registryRepository;

    @Before
    public void setUp() throws Exception {
        mockMvc.perform(post("/registry/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonFromFile("registry.json")))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/registry/search/findById?id=oxford"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateRatio() {
        registryRepository.updateRatio(3L, "oxford");
        Registry registry = registryRepository.findById("oxford");
        assertThat(registry.getRatio(), is(3L));
    }
}
