package uk.gov.hmcts.probate.services.persistence.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;

import java.util.List;
import java.util.Map;

@Service
public class SequenceNumberService {

    @Autowired
    private Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers;

    private static final String SUBMISSION_REFERENCE = "submissionReference";
    private static final String REGISTRY = "registry";
    private static final String SEQUENCE_NUMBER = "sequenceNumber";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";

    @Autowired
    private RegistryRepository registryRepository;
    private final ObjectMapper mapper;

    @Autowired
    public SequenceNumberService(RegistryRepository registryRepository, ObjectMapper mapper) {
        this.registryRepository = registryRepository;
        this.mapper = mapper;
    }

    public Long getNextSequenceNumber(String registryName) {
        try {
            return registrySequenceNumbers.get(registryName).nextLongValue();
        } catch (NullPointerException e) {
                throw new RegistryNotConfiguredException("Registry not configured: " + registryName, e);
        }
    }

    public JsonNode getNextRegistry(long submissionReference) {
        Registry nextRegistry = identifyNextRegistry();
        return populateRegistrySubmitData(submissionReference, nextRegistry);
    }

    JsonNode populateRegistrySubmitData(long submissionReference, Registry registry) {
        ObjectNode registryDataObject = mapper.createObjectNode();
        ObjectNode registryMapper = mapper.createObjectNode();

        registryDataObject.put(SUBMISSION_REFERENCE, submissionReference);
        registryMapper.put(NAME, registry.capitalizeRegistryName());
        registryMapper.put(SEQUENCE_NUMBER, getNextSequenceNumber(registry.getName()));
        registryMapper.put(EMAIL, registry.getEmail());
        registryMapper.put(ADDRESS, registry.getAddress());
        registryDataObject.set(REGISTRY, registryMapper);

        return registryDataObject;
    }

    Registry identifyNextRegistry() {
        List<Registry> registries = registryRepository.findAll();
        return new Registry();
    }

}
