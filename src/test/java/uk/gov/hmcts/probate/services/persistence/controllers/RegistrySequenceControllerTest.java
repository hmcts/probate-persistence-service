package uk.gov.hmcts.probate.services.persistence.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.probate.services.persistence.TestUtils;
import uk.gov.hmcts.probate.services.persistence.services.RegistrySequenceService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class RegistrySequenceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistrySequenceService mockRegistrySequenceService;

    private TestUtils testUtils;

    @Before
    public void setUp() {
        testUtils = new TestUtils();
    }

    @Test
    public void getNext() throws Exception {
        JsonNode registryData = testUtils.getJsonNodeFromFile("registryData.json");

        when(mockRegistrySequenceService.getNextRegistry(anyLong()))
                .thenReturn(registryData);
        this.mvc.perform(get("/registry/" + 1234))
                .andExpect(status().isOk()).andExpect(content().json(registryData.toString()));
    }
}
