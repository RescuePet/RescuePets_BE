package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.*;
import hanghae99.rescuepets.memberpet.service.PetPostCatchService;
import hanghae99.rescuepets.memberpet.service.PetPostMissingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "유기견 발생 신고 API")
@RequestMapping("/api/pets/missing")
@RequiredArgsConstructor
@RestController
public class PetPostMissingController {
    private final PetPostMissingService petPostMissingService;

    //    @ApiOperation(value = "게시글 목록 조회", notes = "page, size, sortBy로 페이징 후 조회")
    @GetMapping("/")
    @Operation(summary = "PostMissing 게시글 불러오기", description = "PostMissing 전체 게시글을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPetPostMissingList(@RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        //sortBy = postLikeCount,
        return petPostMissingService.getPetPostMissingList(page-1, size, sortBy, member);
    }
    @GetMapping("/all")
    @Operation(summary = "PostMissing 게시글 전체(페이징없이) 불러오기", description = "PostMissing 전체 게시글을 페이징없이 불러옵니다.")
    public ResponseEntity<ResponseDto> getPetPostMissingAll(@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        return petPostMissingService.getPetPostMissingAll(sortBy, member);
    }
    @GetMapping("/member")
    @Operation(summary = "내가 작성한 PostMissing 게시글 불러오기", description = "캐시에 저장된 member정보를 기반으로 내가 작성한 PostMissing 게시글들을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPetPostMissingListByMember(@RequestParam int page,
                                                                                  @RequestParam int size,
                                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        //sortBy = postLikeCount,
        return petPostMissingService.getPetPostMissingListByMember(page-1, size, sortBy, member);
    }

    @GetMapping("/{petPostMissingId}")
    @Operation(summary = "특정 PostMissing 게시글 조회하기", description = "URI에 명시된 PostId를 기반으로 특정 PostMissing 게시글을 조회합니다")
    public ResponseEntity<ResponseDto> getPetPostMissing(@PathVariable Long petPostMissingId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostMissingService.getPetPostMissing(petPostMissingId, memberDetails.getMember());
    }
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "PostMissing 게시글 작성하기", description = "PostMissing 게시글 하나를 작성합니다")
    public ResponseEntity<ResponseDto> createPetPostMissing(@ModelAttribute PetPostMissingRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return petPostMissingService.create(requestDto, memberDetails.getMember());
    }
    @PutMapping(value = "/{petPostMissingId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "내가 작성한 특정 PostMissing 게시글 수정하기", description = "내가 작성한 PostMissing 게시글 하나를 수정합니다")
    public ResponseEntity<ResponseDto> updatePetPostMissing(@PathVariable Long petPostMissingId,
                                          @ModelAttribute PetPostMissingRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return petPostMissingService.update(petPostMissingId, requestDto, memberDetails.getMember());
    }
    @DeleteMapping("/{petPostMissingId}")
    @Operation(summary = "내가 작성한 특정 PostMissing 게시글 삭제하기", description = "내가 작성한 PostMissing 게시글 하나를 삭제합니다")
    public ResponseEntity<ResponseDto> deletePetPostMissing(@PathVariable Long petPostMissingId, @AuthenticationPrincipal MemberDetails userDetails) {
        return petPostMissingService.delete(petPostMissingId, userDetails.getMember());
    }

    @PostMapping(value = "/links/{petPostMissingId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "PostMissing 게시글에서 다른 게시글로 링크걸기", description = "사용자가 연결짓고 싶은 게시물 간의 링크를 생성합니다. PostType에 대상 게시물이 CATCH인지 MISSING인지 입력해주어야합니다.")
    public ResponseEntity<ResponseDto> createLink(@PathVariable Long petPostMissingId,
                                                  @ModelAttribute PostLinkRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostMissingService.createLink(petPostMissingId, requestDto, memberDetails.getMember());
    }
    @GetMapping(value = "/links/{petPostMissingId}")
    @Operation(summary = "PostMissing 게시글에서 생성된 링크들을 조회합니다", description = "해당 게시글에서 생성된 링크들을 조회합니다. 게시글에서 생성된 링크가 전혀 없는지, 하나라도 있는지 사용자에게 표시해줍니다.")
    public ResponseEntity<ResponseDto> getLink(@PathVariable Long petPostMissingId,
                                               @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostMissingService.getLink(petPostMissingId, memberDetails.getMember());
    }
    @DeleteMapping(value = "/links/{petPostMissingId}")
    @Operation(summary = "PostMissing 게시글에서 내가 만든 링크를 삭제합니다", description = "해당 게시글에서 생성된 링크 중, 내가 생성한 링크를 일괄 삭제합니다. 연결한 반대편 게시글에서도 링크가 같이 삭제됩니다.")
    public ResponseEntity<ResponseDto> deleteLink(@PathVariable Long petPostMissingId,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostMissingService.deleteLink(petPostMissingId, memberDetails.getMember());
    }
}
