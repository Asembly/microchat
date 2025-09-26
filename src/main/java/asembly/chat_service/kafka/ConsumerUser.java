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
@KafkaListener(topics = "user-events", containerFactory = "chatListener", groupId = "chat")
public class ConsumerUser {

    @Autowired
    private ChatRepository chatRepository;

    @KafkaHandler
    public void handler(UserEvent data){

        var chats = chatRepository.findAllById(data.chats_id());
        switch(data.type())
        {
            case USER_DELETED, USER_LEAVE_CHAT -> {

                chats.forEach(chat -> {
                    chat.getUsers_id().remove(data.user_id());
                });

            }
            case USER_ADD_CHAT -> {
                chats.forEach(chat -> {
                    chat.getUsers_id().add(data.user_id());
                });
            }
        }
        chatRepository.saveAll(chats);
    }

}
