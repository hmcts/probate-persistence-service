package uk.gov.hmcts.probate.services.persistence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.probate.services.persistence.services.SequenceNumberService;

@RestController
public class SequenceNumberController {

    @Autowired
    private SequenceNumberService sequenceNumberService;


    @GetMapping(value = "/sequence-number/{registry}")
    public ResponseEntity<Long> getNext(@PathVariable String registry) {

        return new ResponseEntity<>(sequenceNumberService.getNext(registry), HttpStatus.OK);
    }
}
