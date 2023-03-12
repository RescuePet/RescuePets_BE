package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.service.ChatService;
import hanghae99.rescuepets.common.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void enter(ChatRequestDto requestDto) {
        if (Chat.MessageType.ENTER.equals(requestDto.getType())) {
            requestDto.setMessage(requestDto.getSender() + "님이 입장하였습니다.");
        }
        chatService.createChat(requestDto);
        sendingOperations.convertAndSend("/topic/chat/room/" + requestDto.getRoomId(), requestDto);
    }
}
