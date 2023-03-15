package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.dto.PublicPetsResponsDto;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

//    @SecurityRequirements
//    @PostMapping("/api-save/{pageNo}")
//    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 저장", description = "자세한 설명")
//    public String apiSave(@PathVariable(value = "pageNo") String pageNo,
//                          @RequestParam(value = "state") String state) throws IOException {
//        return publicPetService.apiSave(pageNo, state);
//    }

    //메서드 분리 중
    @SecurityRequirements
    @PostMapping("/api-save/{pageNo}")
    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 저장 진행", description = "자세한 설명")
    public String apiSaves(@PathVariable(value = "pageNo") String pageNo,
                           @RequestParam(value = "state") String state) throws IOException {
        return publicPetService.apiSaves(pageNo, state);
    }


    @SecurityRequirements
    @GetMapping("/info-list")
    @Operation(summary = "유기동물 전체 정보 가져오기", description = "자세한 설명")
    public PublicPetsResponsDto getPublicPet(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sortBy", required = false, defaultValue = "happenDt") String sortBy) {
        return publicPetService.getPublicPet(page - 1, size, sortBy);
    }

    @SecurityRequirements
    @GetMapping("/details/{desertionNo}")
    @Operation(summary = "유기동물 상세 정보 가져오기", description = "자세한 설명")
    public PublicPetResponsDto getPublicPetDetails(@PathVariable(value = "desertionNo") String desertionNo) {
        return publicPetService.getPublicPetDetails(desertionNo);
    }

    //마이페이지에 PetinfoLike 연관관계로 풀어보면 될듯, 관심 등록한 유기동물은 desertionNo로 상세페이지 API를 통해 이동하면 될듯함
    @PostMapping("/likes/{desertionNo}")
    @Operation(summary = "유기동물 관심 등록", description = "자세한 설명")
    public String petInfoLike(@PathVariable(value = "desertionNo") String desertionNo, @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.petInfoLike(desertionNo, memberDetails.getMember());
    }

    @DeleteMapping("/likes/{PetInfoLikeId}")
    @Operation(summary = "유기동물 관심 삭제", description = "자세한 설명")
    public String deletePetInfoLike(@PathVariable(value = "PetInfoLikeId") Long PetInfoLikeId, @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.deletePetInfoLike(PetInfoLikeId, memberDetails.getMember());
    }
}