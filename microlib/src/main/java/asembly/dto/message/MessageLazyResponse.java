package asembly.dto.message;

import java.util.List;

public record MessageLazyResponse(List<MessageResponse> messages, Boolean hasMore){
}
