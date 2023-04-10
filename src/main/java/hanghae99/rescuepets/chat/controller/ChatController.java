package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatResponseDto;
import hanghae99.rescuepets.chat.service.ChatService;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.sse.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final SseService sseService;

    @MessageMapping("/{roomId}")
    @SendTo("/sub/{roomId}")
    public void enter(@DestinationVariable String roomId, ChatRequestDto requestDto) {
        Member receiver = chatService.createChat(roomId, requestDto);
        template.convertAndSend("/sub/" + roomId, ChatResponseDto.of(requestDto));
        sseService.send(receiver, NotificationType.CHAT, requestDto.getSender() + "님이 새로운 채팅을 보냈어요");
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "채팅 조회")
    public ResponseEntity<ResponseDto> chat(@PathVariable String roomId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatService.getMessages(roomId, memberDetails.getMember());
    }
}