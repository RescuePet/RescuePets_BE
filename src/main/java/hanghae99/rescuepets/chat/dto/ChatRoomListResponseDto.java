package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {

    private String roomId;
    private String roomName;
    private String partner;

    public static ChatRoomListResponseDto of(ChatRoom room) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .partner(room.getGuest().getNickname())
                .build();
    }
}
