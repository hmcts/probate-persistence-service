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

    public List<Registry> updateRegistries(List<Registry> registries) {
        List<String> registryData = registryRepository.findAll().stream()
                .map(Registry::getId)
                .collect(Collectors.toList());
        List<String> registryNames = registries.stream()
                .map(Registry::getId)
                .collect(Collectors.toList());

        registries.forEach(r -> {
            if (registryData.contains(r.getId())) {
                registryRepository.updateRatio(r.getRatio(), r.getId());
            } else {
                registryRepository.save(r);
            }
        });
        registryData.removeAll(registryNames);
        registryData.forEach(r -> registryRepository.delete(registryRepository.findById(r)));
        return registries;
    }
}
