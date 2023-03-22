package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom room;

    @ManyToOne
    private Member sender;

    private String message;

    public static Chat of(ChatRequestDto dto, ChatRoom room, Member sender) {
        return Chat.builder()
                .room(room)
                .sender(sender)
                .message(dto.getMessage())
                .build();
    }
}



