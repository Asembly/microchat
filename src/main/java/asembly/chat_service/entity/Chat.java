package asembly.chat_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private String id;

    @NotBlank
    private String title;

    private List<String> users_id;

    private List<String> messages_id;

    @Temporal(TemporalType.DATE)
    private LocalDate created_at;

}
