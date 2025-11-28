package asembly.message_service.exception;

import asembly.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private ErrorResponseParser parser;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(ResourceNotFoundException exception)
    {
        String message = parser.extractErrorMessage(exception.getMessage());
        log.error(message);
        return new ResponseError(HttpStatus.NOT_FOUND, message, LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handle(TokenNotFoundException exception)
    {
        log.error(exception.getMessage());
        return new ResponseError(HttpStatus.UNAUTHORIZED, exception.getMessage(), LocalDateTime.now());
    }
}