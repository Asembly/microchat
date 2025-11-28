package asembly.dto.auth.token;

public record RefreshResponse(String token, Long expires_at){}
