package uk.gov.hmcts.probate.services.persistence.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.probate.services.persistence.services.SequenceNumberService;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class SequenceNumberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SequenceNumberService mockSequenceNumberService;

    @Test
    public void getNext() throws Exception {

        when(mockSequenceNumberService.getNext(anyString()))
                .thenReturn(1234L);

        this.mvc.perform(get("/sequence-number/registryName"))
                .andExpect(status().isOk()).andExpect(content().json("1234"));

    }
}
