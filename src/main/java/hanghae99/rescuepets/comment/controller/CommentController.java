package hanghae99.rescuepets.comment.controller;

import hanghae99.rescuepets.comment.dto.CommentResponseDto;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.comment.service.CommentService;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

//    @GetMapping("/pets/missing/members/{memberId}")
//    public ResponseDto<List<CommentResponseDto>> getCommentByUser(@AuthenticationPrincipal MemberDetails userDetails){
//        return commentService.getCommentList(userDetails.getMember());
//    }
//

    @GetMapping("/pets/missing/comments/{petPostMissingId}")
    public ResponseDto<List<CommentResponseDto>> getCommentByPost(@PathVariable Long petPostMissingId) {
        return commentService.getCommentCurrentList(petPostMissingId);
    }

    @PostMapping("/pets/missing/comments/{petPostMissingId}")
    public ResponseDto<String> createComment(@PathVariable Long petPostMissingId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.create(petPostMissingId, requestDto, userDetails.getMember());
    }

    @PutMapping("/pets/missing/comments/{commentId}")
    public ResponseDto<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }

    @DeleteMapping("/pets/missing/comments/{commentId}")
    public ResponseDto<String> deleteComment(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
}
