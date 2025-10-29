package asembly.chat_service.mapper;

import asembly.chat_service.entity.Chat;
import asembly.dto.chat.ChatResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatResponse chatToChatResponse(Chat chat);
}
