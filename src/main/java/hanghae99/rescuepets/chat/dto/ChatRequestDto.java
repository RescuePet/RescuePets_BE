package hanghae99.rescuepets.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    private String roomId;
    private String message;
    private String sender;

    public void setMessage(String message) {
        this.message = message;
    }
}
