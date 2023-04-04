package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.service.ChatRoomService;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "나의 채팅방 조회")
    @GetMapping("/rooms")
    public ResponseEntity<ResponseDto> rooms(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatRoomService.getRoomList(memberDetails.getMember());
    }

    @Operation(summary = "채팅방 생성")
    @PostMapping("/room/{postId}")
    public String createCatchRoom(@PathVariable Long postId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatRoomService.createChatRoom(postId, memberDetails.getMember());
    }
}