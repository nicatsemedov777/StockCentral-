package az.project.business_management.error.exception;

public class NullValueException extends RuntimeException{
    public NullValueException() {
    }

    public NullValueException(String message) {
        super(message);
    }

    public NullValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullValueException(Throwable cause) {
        super(cause);
    }
}
