package asembly.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message) {
        super(
                "Unauthorized service",
                StatusCodeException.UNAUTHORIZED_SERVICE
        );    }
}
