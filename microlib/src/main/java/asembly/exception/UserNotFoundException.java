package asembly.exception;

import java.util.Map;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(
                "User not found",
                StatusCodeException.USER_NOT_FOUND
        );
    }
}
