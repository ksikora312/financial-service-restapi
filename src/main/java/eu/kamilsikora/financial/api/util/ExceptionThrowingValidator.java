package eu.kamilsikora.financial.api.util;

import eu.kamilsikora.financial.api.errorhandling.ConstraintViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class ExceptionThrowingValidator {
    private final Validator validator;

    public ExceptionThrowingValidator(final Validator validator) {
        this.validator = validator;
    }

    public <T> void validate(T t, Class<?>... groups) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        Set<String> errorMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        if (!errorMessages.isEmpty()) {
            throw new ConstraintViolationException(errorMessages);
        }
    }
}
