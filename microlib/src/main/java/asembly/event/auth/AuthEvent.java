package asembly.event.auth;

public record AuthEvent(String correlation_id,
                        AuthEventType type,
                        String username,
                        String encode_password) {}
