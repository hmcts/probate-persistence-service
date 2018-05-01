package uk.gov.hmcts.probate.services.persistence.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class SequenceNumberServiceTest {

    @Autowired
    @InjectMocks
    private SequenceNumberService sequenceNumberService;

    @Mock
    private Map<String, PostgreSQLSequenceMaxValueIncrementer> registrySequenceNumbers;

    @Mock
    PostgreSQLSequenceMaxValueIncrementer mockIncrementer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getNext() {

        when(this.registrySequenceNumbers.get("ValidName"))
                .thenReturn(mockIncrementer);
        when(this.mockIncrementer.nextLongValue())
                .thenReturn(1234L);

        Long response = sequenceNumberService.getNext("ValidName");

        assertThat(response, is(equalTo(1234L)));
    }

    @Test
    public void getNextInValidName() {

        try {
            sequenceNumberService.getNext("InValidName");
        } catch (RegistryNotConfiguredException e){
            assert(e.getMessage().contains("InValidName"));
        }
    }
}
