package uk.gov.hmcts.probate.services.persistence.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;

@RunWith(MockitoJUnitRunner.class)
public class SequenceNumberServiceTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @InjectMocks
  private SequenceNumberService sequenceNumberService;

  @Mock
  private Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers;

  @Mock
  private PostgresSequenceMaxValueIncrementer mockIncrementer;

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
    expectedException.expect(RegistryNotConfiguredException.class);
    expectedException.expectMessage("InValidName");
    expectedException.expectCause(instanceOf(NullPointerException.class));

    sequenceNumberService.getNext("InValidName");
  }
}
