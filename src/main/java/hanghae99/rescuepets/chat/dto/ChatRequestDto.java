package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {

    public enum MessageType {
        ENTER,
        TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
