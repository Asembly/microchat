package asembly.dto.auth;

public enum AuthStatus {
    VALID,
    INVALID_CREDENTIALS,
    USER_ALREADY_EXIST,
    USER_NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    TIMEOUT_SERVER
}
