package hanghae99.rescuepets.comment.controller;

import hanghae99.rescuepets.comment.dto.CommentControllerResponse;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.comment.dto.CommentResponseDto;
import hanghae99.rescuepets.comment.service.CommentService;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import hanghae99.rescuepets.sse.service.SseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.POST_NOT_FOUND;
import static hanghae99.rescuepets.common.dto.SuccessMessage.COMMENT_WRITING_SUCCESS;

@Tag(name = "코멘트 작성 CRUD API")
@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SseService sseService;

    @GetMapping("/member")
    @Operation(summary = "내가 쓴 댓글 불러오기", description = "")
    public ResponseEntity<ResponseDto> getCommentListByMember(@RequestParam int page,
                                                              @RequestParam int size,
                                                              @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return commentService.getCommentListByMember(page-1, size, userDetails.getMember());
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 하나 댓글 불러오기", description = "")
    public ResponseEntity<ResponseDto> getCommentList(@RequestParam int page,
                                                      @RequestParam int size,
                                                      @PathVariable Long postId) {
        return commentService.getCommentList(page-1, size, postId);
    }

    @PostMapping("/{postId}")
    @Operation(summary = "게시글 댓글 작성하기", description = "")
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createComment(postId, requestDto, memberDetails.getMember());
//        sseService.send(comment.getReceiver(), NotificationType.COMMENT, comment.getReceiver().getNickname() + "님이 댓글을 등록했어요.");
//        return ResponseDto.toResponseEntity(COMMENT_WRITING_SUCCESS, new CommentResponseDto(comment.getComment()));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "게시글에 작성한 댓글 수정하기", description = "")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "게시글에 작성한 댓글 삭제하기", description = "")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
}
