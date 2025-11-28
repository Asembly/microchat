package asembly.dto.auth.token;

public record AccessResponse(String token, Long expires_at){}
