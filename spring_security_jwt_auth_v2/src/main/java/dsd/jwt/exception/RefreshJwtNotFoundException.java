package dsd.jwt.exception;


public class RefreshJwtNotFoundException extends RuntimeException {
    public RefreshJwtNotFoundException(String message) {
        super(message);
    }
}
