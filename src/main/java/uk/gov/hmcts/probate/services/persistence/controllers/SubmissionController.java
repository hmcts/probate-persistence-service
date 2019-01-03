package uk.gov.hmcts.probate.services.persistence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.gov.hmcts.probate.services.persistence.model.Submission;
import uk.gov.hmcts.probate.services.persistence.repository.SubmissionRepository;

import java.util.Optional;

@RepositoryRestController
@ExposesResourceFor(Submission.class)
@RequestMapping(value = "/submissions")
public class SubmissionController {
    @Autowired
    SubmissionRepository submissionRepository;

    @GetMapping(value = "/search/findById")
    public @ResponseBody ResponseEntity<Submission> findById(@RequestParam("id") Long id) {
        Optional<Submission> byId = submissionRepository.findById(id);
        return byId
                .map(sumbissionData -> new ResponseEntity<>(sumbissionData, HttpStatus.FOUND))
                .orElseGet(() ->  new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
