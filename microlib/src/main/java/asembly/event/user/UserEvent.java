package asembly.event.user;

import java.util.List;

public record UserEvent(UserEventType type, String user_id) {}
