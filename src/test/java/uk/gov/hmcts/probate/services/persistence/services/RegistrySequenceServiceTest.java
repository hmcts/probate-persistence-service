package uk.gov.hmcts.probate.services.persistence.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import uk.gov.hmcts.probate.services.persistence.TestUtils;
import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class RegistrySequenceServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Map<String, PostgreSQLSequenceMaxValueIncrementer> registrySequenceNumbers;

    @Mock
    private PostgreSQLSequenceMaxValueIncrementer mockIncrementer;

    @Mock
    private RegistryRepository registryRepository;
    @Mock
    private ObjectMapper mapper;
    @Mock
    private EntityManager entityManager;

    private TestUtils testUtils;
    private RegistrySequenceService sequenceNumberService;
    private List<Registry> registryList;

    private Registry oxford = new Registry();
    private Registry birmingham = new Registry();
    private Registry manchester = new Registry();


    @Before
    public void setUp() {
        oxford.setId("oxford");
        oxford.setEmail("oxford@email.com");
        oxford.setAddress("Test Address Line 1\nTest Address Line 2\nTest Address Postcode");
        oxford.setRatio(3L);
        oxford.setCounter(2L);
        birmingham.setId("birmingham");
        birmingham.setEmail("birmingham@email.com");
        birmingham.setAddress("Birmingham Test Address Line 1\nTest Address Line 2\nTest Address Postcode");
        birmingham.setRatio(3L);
        birmingham.setCounter(3L);
        manchester.setId("manchester");
        manchester.setEmail("manchester@email.com");
        manchester.setAddress("Manchester Test Address Line 1\nTest Address Line 2\nTest Address Postcode");
        manchester.setRatio(3L);
        manchester.setCounter(3L);
        registryList = new ArrayList<>();
        registryList.add(oxford);
        registryList.add(birmingham);
        registryList.add(manchester);
        testUtils = new TestUtils();
        mapper = new ObjectMapper();
        sequenceNumberService = new RegistrySequenceService(registrySequenceNumbers, registryRepository, entityManager, mapper);
    }

    @Test
    public void getNext() {
        when(this.registrySequenceNumbers.get("oxford"))
            .thenReturn(mockIncrementer);
        when(this.mockIncrementer.nextLongValue())
            .thenReturn(1234L);

        Long response = sequenceNumberService.getNextSequenceNumber("oxford");

        assertThat(response, is(equalTo(1234L)));
    }

    @Test
    public void getNextInvalidName() {
        expectedException.expect(RegistryNotConfiguredException.class);
        expectedException.expectMessage("InValidName");
        expectedException.expectCause(instanceOf(NullPointerException.class));

        sequenceNumberService.getNextSequenceNumber("InValidName");
    }

    @Test
    public void identifyNextRegistry() {
        when(registryRepository.findAll()).thenReturn(registryList);
        Registry result = sequenceNumberService.identifyNextRegistry();
        assertThat(result, is(equalTo(oxford)));
    }

    @Test
    public void resetRegistryCounters() {
        when(sequenceNumberService.resetRegistryCounters())
                .thenReturn(registryList);

        List<Registry> result = sequenceNumberService.resetRegistryCounters();
        assertThat(result, is(equalTo(registryList)));
    }

    @Test
    public void getNextRegistry() {
        JsonNode registryData = testUtils.getJsonNodeFromFile("registryData.json");
        when(registryRepository.findAll()).thenReturn(registryList);
        when(this.registrySequenceNumbers.get("oxford"))
                .thenReturn(mockIncrementer);
        when(this.mockIncrementer.nextLongValue())
                .thenReturn(20013L);

        JsonNode result = sequenceNumberService.getNextRegistry(1234);
        assertThat(result.toString(), is(equalTo(registryData.toString())));
    }

    @Test
    public void populateRegistrySubmitData() {
        JsonNode registryData = testUtils.getJsonNodeFromFile("registryData.json");
        when(this.registrySequenceNumbers.get("oxford"))
                .thenReturn(mockIncrementer);
        when(this.mockIncrementer.nextLongValue())
                .thenReturn(20013L);


        JsonNode result = sequenceNumberService.populateRegistrySubmitData(1234, oxford);
        assertThat(result.toString(), is(equalTo(registryData.toString())));
    }
}
