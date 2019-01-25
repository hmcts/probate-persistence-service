package uk.gov.hmcts.probate.services.persistence.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistryTest {
    private Validator validator;
    private Registry registry;

    @Before
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        registry = new Registry();
        registry.setAddress("Petty France");
        registry.setRatio(1L);
        registry.setCounter(1L);
    }

    @Test
    public void shouldAllowOnlyPositiveRatios() {
        registry.setRatio(-1L);
        assertThatValidationFails();
    }

    @Test
    public void shouldAllowOnlyPositiveCounters() {
        registry.setCounter(-1L);
        assertThatValidationFails();
    }

    @Test
    public void shouldEnsureThatRatioIsGreaterThanCounter() {
        registry.setRatio(3L);
        registry.setCounter(4L);
        assertThatValidationFails();
    }

    private void assertThatValidationFails() {
        assertThat(validator.validate(registry).isEmpty()).isFalse();
    }
}
