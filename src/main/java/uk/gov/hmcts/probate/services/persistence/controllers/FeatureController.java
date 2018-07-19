package uk.gov.hmcts.probate.services.persistence.controllers;

import org.ff4j.FF4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {

    private static final Logger logger = LoggerFactory.getLogger(FeatureController.class);
    private static final String ERROR_MESSAGE = "Please try again with a valid feature.";

    @Autowired
    private FF4j ff4j;

    @PostMapping(path = "/feature/{featureName}")
    public void toggleFeature(@PathVariable String featureName) {
        // curl -X POST http://localhost:8282/feature/testFeature
        if (ff4j.exist(featureName)) {
            if (ff4j.check(featureName)) {
                ff4j.disable(featureName);
                logger.info("Feature: {} toggled off", featureName);
            } else {
                ff4j.enable(featureName);
                logger.info("Feature: {} toggled on", featureName);
            }
        } else {
            logger.info("Feature: {} does not exist and has not been registered", featureName);
        }
    }

    @GetMapping(value = "/feature/{featureName}/status")
    public String featureStatus(@PathVariable String featureName) {
        // curl http://localhost:8282/feature/testFeature/status
        if (ff4j.exist(featureName)) {
            if (ff4j.check(featureName)) {
                return "On";
            } else {
                return "Off";
            }
        } else {
            logger.info("Feature: {} does not exist and has not been registered", featureName);
            return ERROR_MESSAGE;
        }
    }
}
