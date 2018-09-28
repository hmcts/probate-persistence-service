package uk.gov.hmcts.probate.services.persistence.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SequenceNumberService {

    @Autowired
    private Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers;
    private RegistryRepository registryRepository;
    private EntityManager entityManager;
    private final ObjectMapper mapper;

    private static final String SUBMISSION_REFERENCE = "submissionReference";
    private static final String REGISTRY = "registry";
    private static final String SEQUENCE_NUMBER = "sequenceNumber";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";

    @Autowired
    public SequenceNumberService(Map<String, PostgresSequenceMaxValueIncrementer> registrySequenceNumbers,
                                 RegistryRepository registryRepository, EntityManager entityManager,
                                 ObjectMapper mapper) {
        this.registrySequenceNumbers = registrySequenceNumbers;
        this.registryRepository = registryRepository;
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    public JsonNode getNextRegistry(long submissionReference) {
        Registry nextRegistry = identifyNextRegistry();
        nextRegistry.incrementCounter();
        entityManager.lock(registryRepository.save(nextRegistry), LockModeType.PESSIMISTIC_WRITE);
        return populateRegistrySubmitData(submissionReference, nextRegistry);
    }

    public Long getNextSequenceNumber(String registryName) {
        try {
            return registrySequenceNumbers.get(registryName).nextLongValue();
        } catch (NullPointerException e) {
            throw new RegistryNotConfiguredException("Registry not configured: " + registryName, e);
        }
    }

    JsonNode populateRegistrySubmitData(long submissionReference, Registry registry) {
        ObjectNode registryDataObject = mapper.createObjectNode();
        ObjectNode registryMapper = mapper.createObjectNode();

        registryDataObject.put(SUBMISSION_REFERENCE, submissionReference);
        registryMapper.put(NAME, registry.capitalizeRegistryName());
        registryMapper.put(SEQUENCE_NUMBER, getNextSequenceNumber(registry.getId()));
        registryMapper.put(EMAIL, registry.getEmail());
        registryMapper.put(ADDRESS, registry.getAddress());
        registryDataObject.set(REGISTRY, registryMapper);

        return registryDataObject;
    }

    Registry identifyNextRegistry() {
        List<Registry> registries = registryRepository.findAll().stream()
                .filter(r -> r.getCounter() < r.getRatio())
                .collect(Collectors.toList());
        return registries.isEmpty() ?
                resetRegistryCounters().get(0) : registries.get(0);
    }

    List<Registry> resetRegistryCounters() {
        return registryRepository.findAll().stream()
                .map(r -> {
                    r.resetCounter();
                    entityManager.lock(registryRepository.save(r), LockModeType.PESSIMISTIC_WRITE);
                    return r;
                })
                .collect(Collectors.toList());
    }
}
