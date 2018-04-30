package uk.gov.hmcts.probate.services.persistence.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

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
    Map<String, PostgreSQLSequenceMaxValueIncrementer> registrySequenceNumbers;

    @Mock
    PostgreSQLSequenceMaxValueIncrementer mockIncrementer;

    @Test
    public void getNext() throws Exception {
        when(this.registrySequenceNumbers.get(anyString()))
                .thenReturn(mockIncrementer);
        when(this.mockIncrementer.nextLongValue())
                .thenReturn(1234L);

        this.mvc.perform(get("/sequence-number/registryName"))
                .andExpect(status().isOk()).andExpect(content().json("1234"));

    }
}
