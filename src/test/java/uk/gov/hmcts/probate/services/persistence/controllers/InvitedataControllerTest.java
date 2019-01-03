package uk.gov.hmcts.probate.services.persistence.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.probate.services.persistence.model.Invitedata;
import uk.gov.hmcts.probate.services.persistence.repository.InvitedataRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.probate.services.persistence.TestUtils.getJsonFromFile;

@RunWith(SpringRunner.class)
@WebMvcTest(InvitedataController.class)
public class InvitedataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitedataRepository invitedataRepository;

    @Test
    public void shouldFindById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Invitedata invitedata = mapper.readValue(getJsonFromFile("inviteData.json"), Invitedata.class);

        when(invitedataRepository.findById(anyString())).thenReturn(Optional.of(invitedata));
        mockMvc.perform(get("/invitedata/search/findById?id=1"))
                .andExpect(status().isFound());
    }
}
