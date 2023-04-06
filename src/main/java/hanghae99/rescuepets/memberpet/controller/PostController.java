package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.MissingPosterRequestDto;
import hanghae99.rescuepets.memberpet.dto.PostRequestDto;
import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
import hanghae99.rescuepets.memberpet.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 게시물 작성")
@RequestMapping("/api/post")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 작성하기", description = "게시글 하나를 작성합니다")
    public ResponseEntity<ResponseDto> createPost(@ModelAttribute PostRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.createPost(requestDto, memberDetails.getMember());
    }

    @PostMapping(value = "/posters/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "실종 게시글 포스터png URL 저장하기", description = "실종 게시글 포스터 이미지 파일의 URL을 저장합니다")
    public ResponseEntity<ResponseDto> setPostPoster(@ModelAttribute MissingPosterRequestDto requestDto,
                                                     @PathVariable Long postId,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.setPostPoster(requestDto, postId, memberDetails.getMember());
    }

    @GetMapping("/list/{postType}")
    @Operation(summary = "전체 게시글 페이징해서 불러오기", description = "카테고리를 지정하여 전체 게시글을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPostList(@RequestParam int page,
                                                   @RequestParam int size,
                                                   @PathVariable String postType,
                                                   @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.getPostList(page - 1, size, postType, memberDetails.getMember());
    }

    @GetMapping("/all")
    @Operation(summary = "전체 게시글 페이징없이 불러오기(지도용)", description = "카테고리 구분 없이 전체 게시글을 페이징없이 불러옵니다")
    public ResponseEntity<ResponseDto> getPostAll() {
        return postService.getPostAll();
    }


    @GetMapping("/member")
    @Operation(summary = "내가 작성한 게시글 불러오기", description = "캐시에 저장된 member정보를 기반으로 내가 작성한 PostCatch 게시글들을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPostListByMember(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.getPostListByMember(page - 1, size, memberDetails.getMember());
    }

    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시글 상세 조회하기", description = "URI에 명시된 PostId를 기반으로 특정 게시글을 조회합니다")
    public ResponseEntity<ResponseDto> getPost(@PathVariable Long postId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.getPost(postId, memberDetails.getMember());
    }

    @GetMapping("/posters/{postId}")
    @Operation(summary = "실종 게시글 포스터png 내려받기", description = "누구든 특정 실종 게시글의 포스터를 내려받습니다")
    public ResponseEntity<ResponseDto> getPostPoster(@PathVariable Long postId) {
        return postService.getPostPoster(postId);
    }

    @GetMapping("/search")
    @Operation(summary = "검색", description = "ex)memberLongitude(126.972828), memberLatitude(37.556817),description(100000)/서울역(위도,경도),100km")
    public ResponseEntity<ResponseDto> getPostListByDistance(@RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam(value = "postType") String postType,
                                                             @RequestParam(value = "memberLongitude",required = false) Double memberLongitude,
                                                             @RequestParam(value = "memberLatitude",required = false) Double memberLatitude,
                                                             @RequestParam(value = "description",required = false) Double description,
                                                             @RequestParam(value = "searchKeyword",required = false) String searchKeyword,
                                                             @RequestParam(value = "searchValue",required = false) String searchValue,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.getPostListByDistance(page, size, postType, memberLongitude, memberLatitude, description,searchKeyword,searchValue, memberDetails.getMember());
    }

    @PutMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정하기", description = "내가 작성한 게시글 하나를 수정합니다")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId,
                                                  @ModelAttribute PostRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.updatePost(postId, requestDto, memberDetails.getMember());
    }

    @DeleteMapping("/temporary/{postId}")
    @Operation(summary = "PostCatch 게시글 임시 삭제하기", description = "내가 작성한 PostCatch 게시글 하나를 삭제합니다")
    public ResponseEntity<ResponseDto> softDeletePetPostCatch(@PathVariable Long postId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return postService.softDeletePost(postId, userDetails.getMember());
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 즉시 삭제하기", description = "내가 작성한 PostCatch 게시글 하나를 삭제합니다")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return postService.deletePost(postId, userDetails.getMember());
    }

    @PostMapping(value = "/links/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글에서 다른 게시글로 링크걸기", description = "사용자가 연결짓고 싶은 게시물 간의 링크를 생성합니다. PostType에 대상 게시물이 CATCH인지 MISSING인지 입력해주어야합니다.")
    public ResponseEntity<ResponseDto> createLink(@PathVariable Long postId,
                                                  @ModelAttribute PostLinkRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.createLink(postId, requestDto, memberDetails.getMember());
    }

    @GetMapping(value = "/links/{postId}")
    @Operation(summary = "게시글에서 생성된 링크들을 조회합니다", description = "해당 게시글에서 생성된 링크들을 조회합니다. 게시글에서 생성된 링크가 전혀 없는지, 하나라도 있는지 사용자에게 표시해줍니다.")
    public ResponseEntity<ResponseDto> getLink(@PathVariable Long postId,
                                               @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.getLink(postId, memberDetails.getMember());
    }

    @DeleteMapping(value = "/links/{postId}")
    @Operation(summary = "게시글에서 내가 만든 링크를 삭제합니다", description = "해당 게시글에서 생성된 링크 중, 내가 생성한 링크를 일괄 삭제합니다. 연결한 반대편 게시글에서도 링크가 같이 삭제됩니다.")
    public ResponseEntity<ResponseDto> deleteLink(@PathVariable Long postId,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return postService.deleteLink(postId, memberDetails.getMember());
    }

}
