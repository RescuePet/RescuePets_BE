package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.SexEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {

    private String roomId;
    private String partner;
    private String lastChat;
    private String profileImage;
    private String postName;
    private String roomName;
    private Long postId;
    private SexEnum sexCd;

    public static ChatRoomListResponseDto of(ChatRoom room, String partner, String lastChat, String profileImage, String postName, Long postId, String roomName, SexEnum sexCd) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .partner(partner)
                .lastChat(lastChat)
                .profileImage(profileImage)
                .postName(postName)
                .postId(postId)
                .roomName(roomName)
                .sexCd(sexCd)
                .build();
    }
}
