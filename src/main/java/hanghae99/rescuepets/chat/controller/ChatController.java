package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatResponseDto;
import hanghae99.rescuepets.chat.service.ChatService;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
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

    @MessageMapping("/{roomId}")
    @SendTo("/sub/{roomId}")
    public void enter(@DestinationVariable String roomId, ChatRequestDto requestDto) {
        chatService.createChat(roomId, requestDto);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "채팅 조회")
    public ResponseEntity<ResponseDto> chat(@PathVariable String roomId) {
        return chatService.getMessages(roomId);
    }
}