package uk.gov.hmcts.probate.services.persistence.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "formdata", path = "formdata")
public interface FormdataRepository extends PagingAndSortingRepository<Formdata, String> {

    Optional<Formdata> findById(@Param("id") String id);

    Formdata findBySubmissionReference(@Param("submissionReference") long submissionReference);
}
