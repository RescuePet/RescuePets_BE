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
    private String lastTime;

    public static ChatRoomListResponseDto of(ChatRoom room, String roomName) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(roomName)
                .lastChat("마지막 채팅")
                .lastTime("시간")
                .build();
    }
}
