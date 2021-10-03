package eu.kamilsikora.financial.api.controller;

import eu.kamilsikora.financial.api.errorhandling.ConstraintViolationException;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Set;

@org.springframework.web.bind.annotation.ControllerAdvice
@ResponseBody
public class ControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstrainViolationException(final ConstraintViolationException exception,
                                                          final WebRequest webRequest) {
        Set<String> errorMessages = exception.getErrorMessages();
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errorMessages);
    }

    @ExceptionHandler(ObjectDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleObjectNotFoundException(final ObjectDoesNotExistException exception,
                                                      final WebRequest webRequest) {
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), Set.of(exception.getMessage()));
    }


    @AllArgsConstructor
    @Getter
    private static class ErrorMessage {
        private final LocalDateTime timestamp;
        private final int status;
        private final Set<String> errorMessages;
    }

}
