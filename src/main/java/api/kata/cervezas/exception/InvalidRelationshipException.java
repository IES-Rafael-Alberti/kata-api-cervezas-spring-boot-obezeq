package api.kata.cervezas.exception;

public class InvalidRelationshipException extends RuntimeException {
    public InvalidRelationshipException(String message) {
        super(message);
    }

    public InvalidRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
