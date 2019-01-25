package uk.gov.hmcts.probate.services.persistence.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistryUpdate {
    @Autowired
    private RegistryRepository registryRepository;

    public List<Registry> updateRegistries(List<Registry> registriesInYaml) {
        List<String> registryDataInDatabase = registryRepository.findAll().stream()
                .map(Registry::getId)
                .collect(Collectors.toList());
        List<String> registryNames = registriesInYaml.stream()
                .map(Registry::getId)
                .collect(Collectors.toList());

        registriesInYaml.forEach(registryInYaml -> {
            if (registryDataInDatabase.contains(registryInYaml.getId())) {
                registryRepository.updateRatio(registryInYaml.getRatio(), registryInYaml.getId());
            } else {
                registryInYaml.setCounter(0L);
                registryRepository.save(registryInYaml);
            }
        });
        registryDataInDatabase.removeAll(registryNames);
        registryDataInDatabase.forEach(r -> registryRepository.delete(registryRepository.findById(r)));
        return registriesInYaml;
    }
}
