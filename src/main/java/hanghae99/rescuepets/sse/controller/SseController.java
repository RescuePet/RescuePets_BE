package hanghae99.rescuepets.sse.controller;

import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.sse.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RequestMapping("/sse")
@RestController
@Tag(name = "SSE")
public class SseController {

    private final SseService sseService;

    @Operation(summary = "SSE 구독")
    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return sseService.subscribe(memberDetails.getMember());
    }
}