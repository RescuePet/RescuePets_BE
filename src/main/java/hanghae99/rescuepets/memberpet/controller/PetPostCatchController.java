package hanghae99.rescuepets.memberpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.service.PetPostCatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequestMapping("/api/pets/catch")
@RequiredArgsConstructor
@RestController
public class PetPostCatchController {
    private final PetPostCatchService petPostCatchService;

//    @ApiOperation(value = "게시글 목록 조회", notes = "page, size, sortBy로 페이징 후 조회")
    @GetMapping("/")
    public ResponseDto<List<PetPostCatchResponseDto>> getPetPostCatchList(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                  @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = null;
        if(memberDetails != null) {
            member = memberDetails.getMember();
        }
        //sortBy = postLikeCount,
        return petPostCatchService.getPetPostCatchList(page-1, size, sortBy, member);
    }

    @PostMapping("/")
    public ResponseDto<String> createPost(@RequestPart PetPostCatchRequestDto requestDto, @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        return petPostCatchService.create(requestDto, multipartFile, memberDetails.getMember());
    }


    @PutMapping("/{petPostCatchId}")
    public ResponseDto<String> updatePost(@PathVariable Long petPostCatchId,
                                          @RequestPart PetPostCatchRequestDto requestDto,
                                          @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal MemberDetails userDetails) throws IOException{
        return petPostCatchService.update(petPostCatchId, requestDto, multipartFile, userDetails.getMember());
    }

    @DeleteMapping("/{petPostCatchId}")
    public ResponseDto<String> deletePost(@PathVariable Long petPostCatchId, @AuthenticationPrincipal MemberDetails userDetails) {
        return petPostCatchService.delete(petPostCatchId, userDetails.getMember());
    }


}
