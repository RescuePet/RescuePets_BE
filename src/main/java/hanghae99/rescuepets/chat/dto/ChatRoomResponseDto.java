package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ChatRoomResponseDto {
    private List<ChatResponseDto> messages = new ArrayList<>();
    private String roomName;

    public static ChatRoomResponseDto of(ChatRoom room) {
        String postName = room.getCatchPost() == null ? "missing-room" : "catch-room";

        return ChatRoomResponseDto.builder()
                .messages(room.getChatMessages().stream().map(ChatResponseDto::of).toList())
                .roomName(postName)
                .build();
    }
}
