package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.Chat;
import hanghae99.rescuepets.common.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponseDto {
    private String sender;
    private String message;
    private String profileImage;

    public static ChatResponseDto of(ChatRequestDto dto, Member sender) {
        return ChatResponseDto.builder()
                .sender(dto.getSender())
                .message(dto.getMessage())
                .profileImage(sender.getProfileImage())
                .build();
    }

    public static ChatResponseDto of(Chat message) {
        return ChatResponseDto.builder()
                .sender(message.getSender().getNickname())
                .message(message.getMessage())
                .profileImage(message.getSender().getProfileImage())
                .build();
    }
}
