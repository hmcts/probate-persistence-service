package uk.gov.hmcts.probate.services.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.hmcts.probate.services.persistence.model.Registry;

import javax.transaction.Transactional;

@RepositoryRestResource(collectionResourceRel = "registry", path = "registry")
public interface RegistryRepository extends JpaRepository<Registry, Long> {
    Registry findById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE registry SET ratio = ?1 WHERE id = ?2")
    void updateRatio(Long ratio, String id);
}
