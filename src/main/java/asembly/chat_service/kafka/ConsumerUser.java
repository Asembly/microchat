package asembly.chat_service.kafka;

import asembly.chat_service.repository.ChatRepository;
import asembly.event.user.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "user-events", containerFactory = "userListener", groupId = "chat-service-group")
public class ConsumerUser {

    @Autowired
    private ChatRepository chatRepository;

    @KafkaHandler
    public void handler(UserEvent data){

        var chats = chatRepository.findChatsByUserId(data.user_id()).orElseThrow();
        switch(data.type())
        {
            case USER_DELETED -> {

                chats.forEach(chat -> {
                    chat.getUsers_id().remove(data.user_id());
                });

            }
        }
        chatRepository.saveAll(chats);
    }

}
