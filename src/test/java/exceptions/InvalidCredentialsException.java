package exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
