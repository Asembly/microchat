package asembly.dto.auth;

public record AuthResult(AuthStatus status, String user_id, String username) {
}
