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
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

//    @GetMapping("/pets/missing/members/{memberId}")
//    public ResponseDto<List<CommentResponseDto>> getCommentByUser(@AuthenticationPrincipal MemberDetails userDetails){
//        return commentService.getCommentList(userDetails.getMember());
//    }

    @GetMapping("/pets/catch/comments/{petPostCatchId}")
    public ResponseDto<List<CommentResponseDto>> getCommentCatch(@PathVariable Long petPostCatchId) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(()->new NullPointerException("게시글이 없는데용"));
        return commentService.getCommentCatchList(petPostCatch);
    }
    @GetMapping("/pets/missing/comments/{petPostMissingId}")
    public ResponseDto<List<CommentResponseDto>> getCommentMissing(@PathVariable Long petPostMissingId) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(()->new NullPointerException("게시글이 없는데용"));
        return commentService.getCommentMissingList(petPostMissing);
    }
    @PostMapping("/pets/catch/comments/{petPostCatchId}")
    public ResponseDto<String> createCommentCatch(@PathVariable Long petPostCatchId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createCommentCatch(petPostCatchId, requestDto, memberDetails.getMember());
    }
    @PostMapping("/pets/missing/comments/{petPostMissingId}")
    public ResponseDto<String> createCommentMissing(@PathVariable Long petPostMissingId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.createCommentMissing(petPostMissingId, requestDto, memberDetails.getMember());
    }
    @PutMapping("/pets/catch/comments/{commentId}")
    public ResponseDto<String> updateCommentCatch(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }
    @PutMapping("/pets/missing/comments/{commentId}")
    public ResponseDto<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.update(commentId, requestDto, userDetails.getMember());
    }
    @DeleteMapping("/pets/catch/comments/{commentId}")
    public ResponseDto<String> deleteCommentCatch(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
    @DeleteMapping("/pets/missing/comments/{commentId}")
    public ResponseDto<String> deleteCommentMissing(@PathVariable Long commentId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return commentService.delete(commentId, userDetails.getMember());
    }
}
