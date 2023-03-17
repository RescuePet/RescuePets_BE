package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom room;

    private String sender;

    private String message;

    public static Chat of(ChatRequestDto dto, ChatRoom room) {
        return Chat.builder()
                .room(room)
                .sender(room.getGuest().getNickname())
                .message(dto.getMessage())
                .build();
    }
}
