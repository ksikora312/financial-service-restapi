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
        Set<ConstraintViolation<T>> tConstraintViolations = validator.validate(t, groups);
        Set<ConstraintViolation<U>> uConstraintViolations = validator.validate(u, groups);
        Stream<ConstraintViolation<T>> tStream = tConstraintViolations.stream();
        Stream<ConstraintViolation<U>> uStream = uConstraintViolations.stream();
        Set<String> errorMessages = Stream.concat(tStream, uStream)
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        if (!errorMessages.isEmpty()) {
            throw new ConstraintViolationException(errorMessages);
        }
    }
}
