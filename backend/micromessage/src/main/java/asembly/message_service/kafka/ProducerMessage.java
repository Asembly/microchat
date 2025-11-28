package asembly.message_service.kafka;

import asembly.event.message.MessageEvent;
import asembly.event.message.MessageEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerMessage {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(MessageEventType type, String message_id, String chat_id)
    {
        MessageEvent event = new MessageEvent(
                type,
                chat_id,
                message_id
        );

        kafkaTemplate.send("message-events", chat_id, event);
        log.info("Message message send: {}\nType: {}", event, event.type());
    }
}
