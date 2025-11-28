package asembly.message_service.client;

import asembly.exception.TokenNotFoundException;
import asembly.message_service.exception.ErrorResponseParser;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Autowired
    private ErrorResponseParser parser;

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        String responseBody = extractResponseBody(response);
        String message = parser.extractErrorMessage(responseBody);

        log.info("{}",message);

        log.error("Feign client error. Method: {}, Status: {}, Body: {}",
                methodKey, status, message);

        switch (status) {
            case BAD_REQUEST:
                return new IllegalArgumentException("Invalid request: " + responseBody);
            case UNAUTHORIZED:
                return new TokenNotFoundException("Access");
            case NOT_FOUND:
                return new ResourceNotFoundException(responseBody);
            case INTERNAL_SERVER_ERROR:
                return new RuntimeException("Internal server error");
            default:
                return new Exception("Unexpected error: " + responseBody);
        }
    }

    private String extractResponseBody(Response response) {
        if (response.body() == null) {
            return "No response body";
        }

        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("Failed to read response body", ex);
            return "Error reading response body";
        }
    }
}