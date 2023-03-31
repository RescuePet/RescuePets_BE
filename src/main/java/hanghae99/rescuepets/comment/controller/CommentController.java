package hanghae99.rescuepets.comment.controller;

import hanghae99.rescuepets.comment.dto.CommentResponseDto;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.comment.service.CommentService;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "코멘트 작성 CRUD API")
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    @GetMapping("/pets/missing/member")
    @Operation(summary = "내가 쓴 댓글 불러오기", description = "")
    public ResponseEntity<ResponseDto> getCommentListByMember(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return commentService.getCommentListByMember(userDetails.getMember());
    }
    @GetMapping("/pets/catch/comments/{petPostCatchId}")
    @Operation(summary = "PostCatch 게시글 하나 댓글 불러오기", description = "")
    public ResponseEntity<ResponseDto> getCommentCatch(@PathVariable Long petPostCatchId) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(()->new NullPointerException("게시글이 없는데용"));
        return commentService.getCommentCatchList(petPostCatch);
    }
    @GetMapping("/pets/missing/comments/{petPostMissingId}")
    @Operation(summary = "PostMissing 게시글 하나 댓글 불러오기", description = "")
    public ResponseEntity<ResponseDto> getCommentMissing(@PathVariable Long petPostMissingId) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(()->new NullPointerException("게시글이 없는데용"));
        return commentService.getCommentMissingList(petPostMissing);
    }
    @PostMapping("/pets/catch/comments/{petPostCatchId}")
    @Operation(summary = "PostCatch 게시글 댓글 작성하기", description = "")
    public ResponseEntity<ResponseDto> createCommentCatch(@PathVariable Long petPostCatchId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createCommentCatch(petPostCatchId, requestDto, memberDetails.getMember());
    }
    @PostMapping("/pets/missing/comments/{petPostMissingId}")
    @Operation(summary = "PostMissing 게시글 댓글 작성하기", description = "")
    public ResponseEntity<ResponseDto> createCommentMissing(@PathVariable Long petPostMissingId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createCommentMissing(petPostMissingId, requestDto, memberDetails.getMember());
    }
    @PutMapping("/pets/catch/comments/{commentId}")
    @Operation(summary = "PostCatch 게시글에 작성한 댓글 수정하기", description = "")
    public ResponseEntity<ResponseDto> updateCommentCatch(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }
    @PutMapping("/pets/missing/comments/{commentId}")
    @Operation(summary = "PostMissing 게시글에 작성한 댓글 수정하기", description = "")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }
    @DeleteMapping("/pets/catch/comments/{commentId}")
    @Operation(summary = "PostCatch 게시글에 작성한 댓글 삭제하기", description = "")
    public ResponseEntity<ResponseDto> deleteCommentCatch(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
    @DeleteMapping("/pets/missing/comments/{commentId}")
    @Operation(summary = "PostMissing 게시글에 작성한 댓글 삭제하기", description = "")
    public ResponseEntity<ResponseDto> deleteCommentMissing(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
}
