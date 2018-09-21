package uk.gov.hmcts.probate.services.persistence.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.probate.services.persistence.services.SequenceNumberService;

@RestController
public class SequenceNumberController {


  private static final Logger logger = LoggerFactory.getLogger(SequenceNumberController.class);

  @Autowired
  private SequenceNumberService sequenceNumberService;


  @GetMapping(value = "/registry/{submissionReference}")
  public ResponseEntity<JsonNode> getNext(@PathVariable Long submissionReference) {
    JsonNode registryData = sequenceNumberService.getNextRegistry(submissionReference);
    logger.info("Request with sequence number: {}, and registry: {} processed",
            registryData.get("registry").get("name"),
            registryData.get("registry").get("sequenceNumber"));
    return new ResponseEntity<>(registryData, HttpStatus.OK);
  }
}
