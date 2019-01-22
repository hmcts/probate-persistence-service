package uk.gov.hmcts.probate.services.persistence.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.gov.hmcts.probate.services.persistence.model.Invitedata;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "invitedata", path = "invitedata")
public interface InvitedataRepository extends PagingAndSortingRepository<Invitedata, String> {

    Optional<Invitedata> findById(@Param("id") String id);

    @RestResource(path = "formdata")
    List<Invitedata> findByFormdataId(@Param("id") String id);
}
