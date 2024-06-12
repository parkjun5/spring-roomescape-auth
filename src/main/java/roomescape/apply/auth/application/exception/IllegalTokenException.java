package roomescape.apply.auth.application.exception;

public class IllegalTokenException extends IllegalArgumentException {
    public IllegalTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}