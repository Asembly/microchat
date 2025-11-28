package asembly.dto.message;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MessageResponse(String id, String text, String author, LocalDateTime created_at) {}

