package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.SexEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {
    private String roomId;
    private String roomName;
    private String partner;
    private String profileImage;
    private String lastChat;
    private String time;
    private Long postId;
    private String postName;
    private SexEnum sexCd;

    public static ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder of(ChatRoom room, String roomName, String partner, String profileImage, Long postId, String postName, SexEnum sexCd) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(roomName)
                .partner(partner)
                .profileImage(profileImage)
                .postId(postId)
                .postName(postName)
                .sexCd(sexCd);
    }
}
