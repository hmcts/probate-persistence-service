package uk.gov.hmcts.probate.services.persistence.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "formdata", path = "formdata")
public interface FormdataRepository extends PagingAndSortingRepository<Formdata, String> {

    Optional<Formdata> findById(@Param("id") String id);

    Formdata findBySubmissionReference(@Param("submissionReference") long submissionReference);

    @Query("select f from Formdata f where  f.creationTime >= :startDate and  f.formdata.ccdCase is null")
    Page<Formdata> findByCreatedAfterDate(@Param("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate, Pageable page);


}
