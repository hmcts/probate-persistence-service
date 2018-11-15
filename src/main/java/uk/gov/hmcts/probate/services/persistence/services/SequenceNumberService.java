package uk.gov.hmcts.probate.services.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.probate.services.persistence.model.RegistryNotConfiguredException;

import java.util.Map;

@Service
public class SequenceNumberService {

    @Autowired
    private Map<String, PostgreSQLSequenceMaxValueIncrementer> registrySequenceNumbers;

    public Long getNext(String registryName) {

        try {
            return registrySequenceNumbers.get(registryName).nextLongValue();
        } catch (NullPointerException e) {
                throw new RegistryNotConfiguredException("Registry not configured: " + registryName, e);
        }
    }
}
