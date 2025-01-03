package az.project.business_management.error.exception;

public class RegistrationVerificationSessionIsExpiredException extends RuntimeException{
    public RegistrationVerificationSessionIsExpiredException() {
    }

    public RegistrationVerificationSessionIsExpiredException(String message) {
        super(message);
    }

    public RegistrationVerificationSessionIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationVerificationSessionIsExpiredException(Throwable cause) {
        super(cause);
    }
}
