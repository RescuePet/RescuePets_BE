package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.dto.ChatRoomResponseDto;
import hanghae99.rescuepets.chat.service.ChatRoomService;
import hanghae99.rescuepets.chat.service.ChatService;
import hanghae99.rescuepets.common.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅방 조회
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomListResponseDto> rooms(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return chatRoomService.getRoomList(memberDetails.getMember());
    }

    //채팅방 생성
    //postCath
    @PostMapping("/room/{postId}")
    @ResponseBody
    public String createRoom(@PathVariable Long postId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return chatRoomService.createCatchRoom(postId, memberDetails.getMember());
    }

    //postMissing
    @PostMapping("/room/{postId}")
    @ResponseBody
    public String createRoom(@PathVariable Long postId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return chatRoomService.createMissingRoom(postId, memberDetails.getMember());
    }



    //채팅 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto roomInfo(@PathVariable String roomId, @AuthenticationPrincipal MemberDetailsImpl userDetails) {
        return chatRoomService.getRoom(roomId, memberDetails.getMember());
    }
}
