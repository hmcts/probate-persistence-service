package uk.gov.hmcts.probate.services.persistence.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class RegistryNotConfiguredException extends RuntimeException{

    private final String message;

    private final Throwable cause;

    public RegistryNotConfiguredException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
