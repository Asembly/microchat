package asembly.dto.message;

public record MessageCreateRequest(String text, String author_id, String chat_id) {}
