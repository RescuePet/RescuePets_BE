package hanghae99.rescuepets.chat.controller;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.service.ChatRoomService;
import hanghae99.rescuepets.common.security.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 조회")
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomListResponseDto> rooms(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatRoomService.getRoomList(memberDetails.getMember());
    }

    @Operation(summary = "catch 채팅방 생성")
    @PostMapping("/catch-room/{catchPostId}")
    @ResponseBody
    public String createCatchRoom(@PathVariable Long catchPostId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatRoomService.createCatchRoom(catchPostId, memberDetails.getMember());
    }

    @Operation(summary = "missing 채팅방 생성")
    @PostMapping("/missing-room/{missingPostId}")
    @ResponseBody
    public String createMissingRoom(@PathVariable Long missingPostId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return chatRoomService.createMissingRoom(missingPostId, memberDetails.getMember());
    }
}