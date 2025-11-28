package asembly.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class BusinessException extends RuntimeException {
    private final StatusCodeException errorCode;
    private final Map<String, Object> details;

    public BusinessException(String message, StatusCodeException errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    public BusinessException(String message, StatusCodeException errorCode, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
}
