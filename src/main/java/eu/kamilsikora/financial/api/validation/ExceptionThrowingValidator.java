package eu.kamilsikora.financial.api.validation;

import eu.kamilsikora.financial.api.errorhandling.ConstraintViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionThrowingValidator {
    private final Validator validator;

    public ExceptionThrowingValidator(final Validator validator) {
        this.validator = validator;
    }

    public <T, U> void validate(T t, U u, Class<?>... groups) throws ConstraintViolationException {
        final Set<ConstraintViolation<T>> tConstraintViolations = validator.validate(t, groups);
        final Set<ConstraintViolation<U>> uConstraintViolations = validator.validate(u, groups);
        final Stream<ConstraintViolation<T>> tStream = tConstraintViolations.stream();
        final Stream<ConstraintViolation<U>> uStream = uConstraintViolations.stream();
        final Set<String> errorMessages = Stream.concat(tStream, uStream)
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        if (!errorMessages.isEmpty()) {
            throw new ConstraintViolationException(errorMessages);
        }
    }

    public <T> void validate(T t, Class<?>... groups) {
        final Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        final Set<String> errorMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        if(!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(errorMessages);
        }
    }
}
