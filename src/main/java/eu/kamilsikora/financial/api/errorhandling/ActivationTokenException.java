package eu.kamilsikora.financial.api.errorhandling;

public class ActivationTokenException extends RuntimeException {
    public ActivationTokenException(String message) {
        super(message);
    }
}
