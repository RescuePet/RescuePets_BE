package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatRoomResponseDto;
import hanghae99.rescuepets.chat.service.ChatService;
import hanghae99.rescuepets.common.security.MemberDetails;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    //채팅
    @MessageMapping("/{roomId}")
    @SendTo("/sub/{roomId}")
    public void enter(@DestinationVariable String roomId, ChatRequestDto requestDto) {
        if (requestDto.getType().equals(ChatRequestDto.MessageType.ENTER)) {
            requestDto.setMessage(requestDto.getSender() + "님이 입장하였습니다.");
        }
        chatService.createChat(roomId, requestDto);
        template.convertAndSend("/sub/" + roomId, requestDto);
    }

    //채팅 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto chat(@PathVariable String roomId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatService.getMessages(roomId, memberDetails.getMember());
    }
}
//
//
//

//
//
//

//
//
//
//