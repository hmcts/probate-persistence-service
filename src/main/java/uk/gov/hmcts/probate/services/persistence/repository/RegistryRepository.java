package uk.gov.hmcts.probate.services.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.hmcts.probate.services.persistence.model.Registry;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "registry", path = "registry")
public interface RegistryRepository extends JpaRepository<Registry, Long> {

    Registry findById(@Param("id") Long id);

    Registry findByName(@Param("name") String name);

    List<Registry> findAll();
}
