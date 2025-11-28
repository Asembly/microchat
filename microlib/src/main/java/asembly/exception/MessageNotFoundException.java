package asembly.exception;

import java.util.Map;

public class MessageNotFoundException extends BusinessException {
    public MessageNotFoundException() {
        super(
                "Message not found",
                StatusCodeException.MESSAGE_NOT_FOUND
        );
    }
}