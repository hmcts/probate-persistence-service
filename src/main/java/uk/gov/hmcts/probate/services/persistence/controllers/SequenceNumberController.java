package uk.gov.hmcts.probate.services.persistence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SequenceNumberController {

    @Autowired
    private Map<String, PostgreSQLSequenceMaxValueIncrementer> registrySequenceNumbers;

    @GetMapping(value = "/sequence-number/{registry}")
    public Long getNext(@PathVariable String registry) {

        return registrySequenceNumbers.get(registry).nextLongValue();
    }
}
