package eu.kamilsikora.financial.api.controller;

import eu.kamilsikora.financial.api.dto.ErrorDto;
import eu.kamilsikora.financial.api.errorhandling.ConstraintViolationException;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
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

    // TODO: handle ActivationTokenException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstrainViolationException(final ConstraintViolationException exception,
                                                      final WebRequest webRequest) {
        Set<String> errorMessages = exception.getErrorMessages();
        return new ErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errorMessages);
    }

    @ExceptionHandler(ObjectDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleObjectNotFoundException(final ObjectDoesNotExistException exception,
                                                  final WebRequest webRequest) {
        return new ErrorDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), Set.of(exception.getMessage()));
    }
}
