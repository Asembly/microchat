package asembly.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseError(
        HttpStatus status,
        String message,
        LocalDateTime time
){}