package uk.gov.hmcts.probate.services.persistence.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;
import uk.gov.hmcts.probate.services.persistence.repository.FormdataRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.probate.services.persistence.TestUtils.getJsonFromFile;

@RunWith(SpringRunner.class)
@WebMvcTest(FormdataController.class)
public class FormdataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormdataRepository formdataRepository;

    @Test
    public void shouldFindById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Formdata formdata = mapper.readValue(getJsonFromFile("formData.json"), Formdata.class);

        when(formdataRepository.findById(anyString())).thenReturn(Optional.of(formdata));
        mockMvc.perform(get("/formdata/search/findById?id=1"))
                .andExpect(status().isFound());
    }
}
