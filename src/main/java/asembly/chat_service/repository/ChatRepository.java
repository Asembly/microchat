package asembly.chat_service.repository;

import asembly.chat_service.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query(value = "select * from chats where :users_id = any(users_id)", nativeQuery = true)
    public Optional<List<Chat>> findChatsByUserId(String users_id);
}