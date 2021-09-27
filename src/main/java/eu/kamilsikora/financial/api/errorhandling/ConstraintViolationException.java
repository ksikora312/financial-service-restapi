package eu.kamilsikora.financial.api.errorhandling;

import lombok.Getter;

import java.util.Set;

@Getter
public class ConstraintViolationException extends RuntimeException {

    private final Set<String> errorMessages;

    public ConstraintViolationException(final Set<String> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }
}
