package asembly.user_service.exception;

import asembly.exception.ResponseError;
import asembly.exception.UserAlreadyExistException;
import asembly.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(UserNotFoundException exception)
    {
       log.error(exception.getMessage());
       return new ResponseError(HttpStatus.NOT_FOUND,exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handle(UserAlreadyExistException exception)
    {
        log.error(exception.getMessage());
        return new ResponseError(HttpStatus.FORBIDDEN,exception.getMessage(), LocalDateTime.now());
    }
}
