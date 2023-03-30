package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
import hanghae99.rescuepets.memberpet.service.PetPostCatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유기견 의심 발견 신고 API")
@RequestMapping("/api/pets/catch")
@RequiredArgsConstructor
@RestController
public class PetPostCatchController {
    private final PetPostCatchService petPostCatchService;

//    @ApiOperation(value = "게시글 목록 조회", notes = "page, size, sortBy로 페이징 후 조회")
    @GetMapping("/")
    @Operation(summary = "PostCatch 전체 게시글 불러오기", description = "PostCatch 전체 게시글을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPetPostCatchList(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        return petPostCatchService.getPetPostCatchList(page-1, size, sortBy, member);
    }
    @GetMapping("/all")
    @Operation(summary = "PostMissing 전체 게시글 페이징없이 불러오기", description = "PostMissing 전체 게시글을 페이징없이 불러옵니다")
    public ResponseEntity<ResponseDto> getPetPostCatchAll(@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        return petPostCatchService.getPetPostCatchAll(sortBy, member);
    }
    @GetMapping("/member")
    @Operation(summary = "내가 작성한 PostCatch 게시글 불러오기", description = "캐시에 저장된 member정보를 기반으로 내가 작성한 PostCatch 게시글들을 페이징하여 불러옵니다")
    public ResponseEntity<ResponseDto> getPetPostCatchListByMember(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        return petPostCatchService.getPetPostCatchListByMember(page-1, size, sortBy, member);
    }

    @GetMapping("/{petPostCatchId}")
    @Operation(summary = "특정 PostCatch 게시글 조회하기", description = "URI에 명시된 PostId를 기반으로 특정 PostCatch 게시글을 조회합니다")
    public ResponseEntity<ResponseDto> getPetPostCatch(@PathVariable Long petPostCatchId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        return petPostCatchService.getPetPostCatch(petPostCatchId, member);
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "PostCatch 게시글 작성하기", description = "PostCatch 게시글 하나를 작성합니다")
    public ResponseEntity<ResponseDto> createPetPostCatch(@ModelAttribute PetPostCatchRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.createPetPostCatch(requestDto, memberDetails.getMember());
    }

    @PutMapping(value = "/{petPostCatchId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "내가 작성한 특정 PostCatch 게시글 수정하기", description = "내가 작성한 PostCatch 게시글 하나를 수정합니다")
    public ResponseEntity<ResponseDto> updatePetPostCatch(@PathVariable Long petPostCatchId,
                                          @ModelAttribute PetPostCatchRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.updatePetPostCatch(petPostCatchId, requestDto, memberDetails.getMember());
    }

//    @DeleteMapping("/{petPostCatchId}")
//    @Operation(summary = "PostCatch 게시글 임시 삭제하기", description = "내가 작성한 PostCatch 게시글 하나를 삭제합니다")
//    public ResponseEntity<ResponseDto> softDeletePetPostCatch(@PathVariable Long petPostCatchId, @AuthenticationPrincipal MemberDetails userDetails) {
//        return petPostCatchService.softDeletePetPostCatch(petPostCatchId, userDetails.getMember());
//    }
    @DeleteMapping("/{petPostCatchId}")
    @Operation(summary = "PostCatch 게시글 즉시 삭제하기", description = "내가 작성한 PostCatch 게시글 하나를 삭제합니다")
    public ResponseEntity<ResponseDto> deletePetPostCatch(@PathVariable Long petPostCatchId,@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails) {
        return petPostCatchService.deletePetPostCatch(petPostCatchId, userDetails.getMember());
    }

    @PostMapping(value = "/links/{petPostCatchId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "PostCatch 게시글에서 다른 게시글로 링크걸기", description = "사용자가 연결짓고 싶은 게시물 간의 링크를 생성합니다. PostType에 대상 게시물이 CATCH인지 MISSING인지 입력해주어야합니다.")
    public ResponseEntity<ResponseDto> createLink(@PathVariable Long petPostCatchId,
                                                  @ModelAttribute PostLinkRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.createLink(petPostCatchId, requestDto, memberDetails.getMember());
    }
    @GetMapping(value = "/links/{petPostCatchId}")
    @Operation(summary = "PostCatch 게시글에서 생성된 링크들을 조회합니다", description = "해당 게시글에서 생성된 링크들을 조회합니다. 게시글에서 생성된 링크가 전혀 없는지, 하나라도 있는지 사용자에게 표시해줍니다.")
    public ResponseEntity<ResponseDto> getLink(@PathVariable Long petPostCatchId,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.getLink(petPostCatchId, memberDetails.getMember());
    }
    @DeleteMapping(value = "/links/{petPostCatchId}")
    @Operation(summary = "PostCatch 게시글에서 내가 만든 링크를 삭제합니다", description = "해당 게시글에서 생성된 링크 중, 내가 생성한 링크를 일괄 삭제합니다. 연결한 반대편 게시글에서도 링크가 같이 삭제됩니다.")
    public ResponseEntity<ResponseDto> deleteLink(@PathVariable Long petPostCatchId,
                                               @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.deleteLink(petPostCatchId, memberDetails.getMember());
    }

}
