package asembly.message_service.kafka;

import asembly.event.chat.ChatEvent;
import asembly.exception.MessageNotFoundException;
import asembly.message_service.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "chat-events", containerFactory = "chatListener", groupId = "chat")
public class ConsumerChat {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaHandler
    public void handler(ChatEvent data){

        var messages = messageRepository.findByChatId(data.chat_id())
                .orElseThrow(MessageNotFoundException::new);

       switch(data.type()){
           case CHAT_DELETED -> {
               messages.forEach(msg -> {
                   messageRepository.delete(msg);
               });
           }
       }
    }
}
