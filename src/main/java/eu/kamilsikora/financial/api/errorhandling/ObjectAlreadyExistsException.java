package eu.kamilsikora.financial.api.errorhandling;


public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(final String message) {
        super(message);
    }
}
