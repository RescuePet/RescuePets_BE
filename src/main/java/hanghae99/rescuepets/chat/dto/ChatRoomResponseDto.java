package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ChatRoomResponseDto {
    private String title;
    private String partner;
    List<ChatResponseDto> dto = new ArrayList<>();

    public static ChatRoomResponseDto of(ChatRoom room, Member member) {
        return ChatRoomResponseDto.builder()
//                .title(room.getCatchPost().getTitle)
                .partner(member.getNickname())
                .dto(room.getChatMessages().stream().map(ChatResponseDto::of).toList())
                .build();
    }
}
