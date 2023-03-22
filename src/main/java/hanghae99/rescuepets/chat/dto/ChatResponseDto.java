package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.Chat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponseDto {
    private String sender;
    private String message;
    private String profileImage;

    public static ChatResponseDto of(ChatRequestDto dto) {
        return ChatResponseDto.builder()
                .sender(dto.getSender())
                .message(dto.getMessage())
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
