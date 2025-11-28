package asembly.dto.user;

import java.time.LocalDate;

public record UserResponse(String id, String username, String password, LocalDate created_at){}
