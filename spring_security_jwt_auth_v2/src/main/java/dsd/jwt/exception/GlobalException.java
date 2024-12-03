package dsd.jwt.exception;

import dsd.jwt.data.response.ServerErrorResponse;
import dsd.jwt.data.response.ServerErrorResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String redirectErrorPage() {
        return "redirect:/home/main";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ServerErrorResponseData("VALIDATION_ERROR", errorMessage));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ServerErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ServerErrorResponseData("USER_ALREADY_EXISTS", ex.getMessage()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ServerErrorResponse> handleEmailNotFound(EmailNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ServerErrorResponseData("EMAIL_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(RefreshJwtNotFoundException.class)
    public ResponseEntity<ServerErrorResponse> handleRefreshJwtNotFound(RefreshJwtNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ServerErrorResponseData("REFRESH_JWT_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ServerErrorResponse> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ServerErrorResponseData("VALIDATION_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ServerErrorResponseData("INTERNAL_SERVER_ERROR", "An unexpected error occurred"));
    }

}
