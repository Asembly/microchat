package asembly.dto.chat;

import java.time.LocalDate;

public record ChatResponse(String id, String title, LocalDate created_at) {}
