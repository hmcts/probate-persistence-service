package uk.gov.hmcts.probate.services.persistence.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;
import uk.gov.hmcts.probate.services.persistence.model.Submission;

@RepositoryRestResource(collectionResourceRel = "submissions", path = "submissions")
public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long> {

    Submission findById(@Param("id") Long id);
}
