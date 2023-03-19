package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {

    private String roomId;
    private String roomName;
    private String lastChat;

    public static ChatRoomListResponseDto of(ChatRoom room, String roomName, String lastChat) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(roomName)
                .lastChat(lastChat)
                .build();
    }
}
