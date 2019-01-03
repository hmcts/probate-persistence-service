package uk.gov.hmcts.probate.services.persistence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.gov.hmcts.probate.services.persistence.model.Invitedata;
import uk.gov.hmcts.probate.services.persistence.repository.InvitedataRepository;

import java.util.Optional;

@RepositoryRestController
@ExposesResourceFor(Invitedata.class)
@RequestMapping(value = "/invitedata")
public class InvitedataController {
    @Autowired
    InvitedataRepository invitedataRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/search/findById")
    public @ResponseBody ResponseEntity<Invitedata> findById(@RequestParam("id") String id) {
        Optional<Invitedata> byId = invitedataRepository.findById(id);
        return byId
                .map(inviteData -> new ResponseEntity<>(inviteData, HttpStatus.FOUND))
                .orElseGet(() ->  new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
