package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
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
    private Long postId;
    private String postName;
    private SexEnum sexCd;
    private String lastChat;
    private String time;
    private int unreadChat;

    public static ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder of(ChatRoom room, Member partner) {
        return ChatRoomListResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getPost().getKindCd())
                .partner(partner.getNickname())
                .profileImage(partner.getProfileImage())
                .postId(room.getPost().getId())
                .postName(room.getPost().getPostType().toString())
                .sexCd(room.getPost().getSexCd());
    }
}
