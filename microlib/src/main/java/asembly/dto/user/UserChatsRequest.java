package asembly.dto.user;

import java.util.List;

public record UserChatsRequest(List<String> chats_id) {}
