package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingResponseDto;
import hanghae99.rescuepets.memberpet.service.PetPostCatchService;
import hanghae99.rescuepets.memberpet.service.PetPostMissingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequestMapping("/api/pets/missing")
@RequiredArgsConstructor
@RestController
public class PetPostMissingController {
    private final PetPostMissingService petPostMissingService;

    //    @ApiOperation(value = "게시글 목록 조회", notes = "page, size, sortBy로 페이징 후 조회")
    @GetMapping("/")
    @Operation(summary = "PostMissing 게시글 불러오기", description = "")
    public ResponseDto<List<PetPostMissingResponseDto>> getPetPostMissingList(@RequestParam int page,
                                                                              @RequestParam int size,
                                                                              @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                              @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        //sortBy = postLikeCount,
        return petPostMissingService.getPetPostMissingList(page-1, size, sortBy, member);
    }
    @GetMapping("/member")
    @Operation(summary = "내가 작성한 PostMissing 게시글 불러오기", description = "")
    public ResponseDto<List<PetPostMissingResponseDto>> getPetPostMissingListByMember(@RequestParam int page,
                                                                                  @RequestParam int size,
                                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        //sortBy = postLikeCount,
        return petPostMissingService.getPetPostMissingListByMember(page-1, size, sortBy, member);
    }

    @GetMapping("/{petPostMissingId}")
    @Operation(summary = "내가 작성한 특정 PostMissing 게시글 불러오기", description = "")
    public ResponseDto<PetPostMissingResponseDto> getPetPostMissing(@PathVariable Long petPostMissingId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return petPostMissingService.getPetPostMissing(petPostMissingId, memberDetails.getMember());
    }
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "", description = "")
    public ResponseDto<String> createPetPostMissing(@ModelAttribute PetPostMissingRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return petPostMissingService.create(requestDto, memberDetails.getMember());
    }


    @PutMapping(value = "/{petPostMissingId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "내가 작성한 특정 PostMissing 게시글 수정하기", description = "")
    public ResponseDto<String> updatePetPostMissing(@PathVariable Long petPostMissingId,
                                          @ModelAttribute PetPostMissingRequestDto requestDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return petPostMissingService.update(petPostMissingId, requestDto, memberDetails.getMember());
    }

    @DeleteMapping("/{petPostMissingId}")
    @Operation(summary = "내가 작성한 특정 PostMissing 게시글 삭제하기", description = "")
    public ResponseDto<String> deletePetPostMissing(@PathVariable Long petPostMissingId, @AuthenticationPrincipal MemberDetails userDetails) {
        return petPostMissingService.delete(petPostMissingId, userDetails.getMember());
    }



}
