package hanghae99.rescuepets.chat.dto;

import hanghae99.rescuepets.common.entity.Chat;
import hanghae99.rescuepets.common.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ChatResponseDto {
    private String sender;
    private String message;
    private String profileImage;
    private String chatTime;

    public static ChatResponseDto of(ChatRequestDto requestDto) {
        return ChatResponseDto.builder()
                .sender(requestDto.getSender())
                .message(requestDto.getMessage())
                .profileImage(requestDto.getProfileImage())
                .chatTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }

    public static ChatResponseDto of(Chat message) {
        return ChatResponseDto.builder()
                .sender(message.getSender().getNickname())
                .message(message.getMessage())
                .profileImage(message.getSender().getProfileImage())
                .chatTime(message.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }
}
