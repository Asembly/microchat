package asembly.message_service.repository;

import asembly.message_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {

    @Query(value = "select * from messages where chat_id = :chat_id", nativeQuery = true)
    public Optional<List<Message>> findByChatId(String chat_id);


    @Query(value = "select * from messages where chat_id = :chat_id order by created_at desc limit :limit", nativeQuery = true)
    public Optional<List<Message>> findNewByChatId(String chat_id, Integer limit);

    @Query(value = "select * from messages where chat_id = :chat_id and created_at < :cursorDate order by created_at limit :limit", nativeQuery = true)
    public Optional<List<Message>> findOldByChatId(String chat_id, LocalDateTime cursorDate, Integer limit);
}
