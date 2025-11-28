package asembly.message_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private String id;

    @NotBlank
    @Lob
    private String text;

    @NotBlank
    private String author_id;

    @NotBlank
    private String chat_id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

}
