package asembly.exception;

import java.util.Map;

public class ChatNotFoundException extends BusinessException {
    public ChatNotFoundException() {
        super(
                "Chat not found",
                StatusCodeException.CHAT_NOT_FOUND
        );
    }
}
