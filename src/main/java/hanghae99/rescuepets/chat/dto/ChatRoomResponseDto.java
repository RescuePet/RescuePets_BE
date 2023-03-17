package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ChatRoomResponseDto {
    private String title;
    List<ChatResponseDto> dto = new ArrayList<>();

    public static ChatRoomResponseDto of(ChatRoom room) {
        return ChatRoomResponseDto.builder()
                .title(room.getHost().getNickname())
                .dto(room.getChatMessages().stream().map(ChatResponseDto::of).toList())
                .build();
    }
}
