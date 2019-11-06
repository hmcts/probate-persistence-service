package uk.gov.hmcts.probate.services.persistence.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;
import uk.gov.hmcts.probate.services.persistence.model.Invitedata;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "invitedata", path = "invitedata")
public interface InvitedataRepository extends PagingAndSortingRepository<Invitedata, String> {

    Optional<Invitedata> findById(@Param("id") String id);

    @RestResource(path = "formdata")
    List<Invitedata> findByFormdataId(@Param("id") String id);

    @Query("select i from invitedata i where  i.formdataId = :formdataId")
    List<Invitedata> findByFormDataId(@Param("formdataId")String formdataId);

    @Query("select i from invitedata i where  i.creationTime >= :startDate")
    Page<Invitedata> findByCreatedAfterDate(@Param("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, Pageable page);
}
