package asembly.chat_service.kafka;

import asembly.chat_service.repository.ChatRepository;
import asembly.event.message.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "message-events", containerFactory = "messageListener", groupId = "chat-service-group")
public class ConsumerMessage {

    @Autowired
    private ChatRepository chatRepository;

    @KafkaHandler
    public void handler(MessageEvent data){

        var chat = chatRepository.findById(data.chat_id()).orElseThrow();
        switch(data.type())
        {
            case MESSAGE_CREATED -> {
                chat.getMessages_id().add(data.message_id());
            }
            case MESSAGE_DELETED -> {
                chat.getMessages_id().remove(data.message_id());
            }
        }
        chatRepository.save(chat);
    }
}
