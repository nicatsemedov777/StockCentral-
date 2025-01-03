package az.project.business_management.error.exception;

public class ProductIsOutOfStockException extends RuntimeException{
    public ProductIsOutOfStockException() {
    }

    public ProductIsOutOfStockException(String message) {
        super(message);
    }

    public ProductIsOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIsOutOfStockException(Throwable cause) {
        super(cause);
    }
}
