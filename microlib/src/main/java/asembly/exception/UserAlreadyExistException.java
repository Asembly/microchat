package asembly.exception;

import java.util.Map;

public class UserAlreadyExistException extends BusinessException{
    public UserAlreadyExistException() {
        super(
                "User already exist",
                StatusCodeException.USER_ALREADY_EXIST
        );
    }
}
