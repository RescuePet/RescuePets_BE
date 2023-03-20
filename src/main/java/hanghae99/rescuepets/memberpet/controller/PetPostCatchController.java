package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.service.PetPostCatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;


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
        return petPostCatchService.create(requestDto, memberDetails.getMember());
    }

    @PutMapping(value = "/{petPostCatchId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "내가 작성한 특정 PostCatch 게시글 수정하기", description = "내가 작성한 PostCatch 게시글 하나를 수정합니다")
    public ResponseEntity<ResponseDto> updatePetPostCatch(@PathVariable Long petPostCatchId,
                                          @ModelAttribute PetPostCatchRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostCatchService.update(petPostCatchId, requestDto, memberDetails.getMember());
    }

    @DeleteMapping("/{petPostCatchId}")
    @Operation(summary = "내가 작성한 특정 PostCatch 게시글 삭제하기", description = "내가 작성한 PostCatch 게시글 하나를 삭제합니다")
    public ResponseEntity<ResponseDto> deletePetPostCatch(@PathVariable Long petPostCatchId, @AuthenticationPrincipal MemberDetails userDetails) {
        return petPostCatchService.delete(petPostCatchId, userDetails.getMember());
    }
}
