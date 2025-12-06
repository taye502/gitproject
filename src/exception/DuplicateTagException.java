package exception;

public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException(String message) {
        super(message);
    }
}
