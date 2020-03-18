package uk.gov.hmcts.probate.services.persistence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.gov.hmcts.probate.services.persistence.model.Formdata;
import uk.gov.hmcts.probate.services.persistence.repository.FormdataRepository;

import java.util.Optional;

@RepositoryRestController
@ExposesResourceFor(Formdata.class)
@RequestMapping(value = "/formdata")
public class FormdataController {
    @Autowired
    FormdataRepository formdataRepository;

    @GetMapping(value = "/search/findById")
    public @ResponseBody ResponseEntity<Formdata> findById(@RequestParam("id") String id) {
        Optional<Formdata> byId = formdataRepository.findById(id);
        return byId
                .map(formData -> new ResponseEntity<>(formData, HttpStatus.FOUND))
                .orElseGet(() ->  new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
