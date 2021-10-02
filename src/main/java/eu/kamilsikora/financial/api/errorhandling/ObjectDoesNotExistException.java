package eu.kamilsikora.financial.api.errorhandling;

public class ObjectDoesNotExistException extends RuntimeException {
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
