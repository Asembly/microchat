package asembly.exception;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(String tokenName) {
        super(
                tokenName + " token not found",
                StatusCodeException.USER_NOT_FOUND
        );
    }
}
