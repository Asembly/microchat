package asembly.dto.chat;

import java.util.List;

public record ChatCreateRequest(String title, List<String> users_id) {}
