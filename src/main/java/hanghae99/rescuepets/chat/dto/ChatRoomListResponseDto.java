package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatMember;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {

    private String roomId;
    private String roomName;
    private String partner;

    public static ChatRoomListResponseDto of(ChatMember chatMember) {
        return ChatRoomListResponseDto.builder()
                .roomId(chatMember.getRoom().getRoomId())
                .roomName(chatMember.getRoom().getRoomName())
                .partner(chatMember.getPartner())
                .build();
    }
}
