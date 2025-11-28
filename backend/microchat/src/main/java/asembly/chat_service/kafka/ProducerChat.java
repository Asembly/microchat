package asembly.chat_service.kafka;

import asembly.event.chat.ChatEvent;
import asembly.event.chat.ChatEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProducerChat {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(ChatEventType type, String chat_id, List<String> users_id)
    {
        ChatEvent event = new ChatEvent(
                type,
                chat_id,
                users_id
        );

        kafkaTemplate.send("chat-events",chat_id,event);
        log.info("Chat message send: {}\nType: {}", event, event.type());
    }
}
