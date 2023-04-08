package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

    @GetMapping("/info-list")
    @Operation(summary = "유기동물 전체 정보 가져오기")
    public ResponseEntity<ResponseDto> getPublicPet(@RequestParam(value = "page") int page,
                                                    @RequestParam(value = "size") int size,
                                                    @RequestParam(value = "sortBy", required = false, defaultValue = "happenDt") String sortBy,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPet(page - 1, size, sortBy, memberDetails.getMember());
    }

    @GetMapping("/details/{desertionNo}")
    @Operation(summary = "유기동물 상세 정보 가져오기")
    public ResponseEntity<ResponseDto> getPublicPetDetails(@PathVariable(value = "desertionNo") String desertionNo,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPetDetails(desertionNo, memberDetails.getMember());
    }

    @PostMapping("/inquiry/{desertionNo}")
    @Operation(summary = "유기동물 문의내역 기록")
    public ResponseEntity<ResponseDto> petInfoInquiry(@PathVariable(value = "desertionNo") String desertionNo,
                                                      @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.petInfoInquiry(desertionNo, memberDetails.getMember());
    }
    @GetMapping("/search")
    @Operation(summary = "공공검색", description = "ex)memberLongitude(126.972828), memberLatitude(37.556817),description(100000)/서울역(위도,경도),100km/kindCd=개")
    public ResponseEntity<ResponseDto> getapiListByDistance(@RequestParam int page,
                                                            @RequestParam int size,
                                                            @RequestParam(value = "Longitude", required = false) Double Latitude,
                                                            @RequestParam(value = "Latitude", required = false) Double Longitude,
                                                            @RequestParam(value = "description", required = false) Double description,
                                                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                                            @RequestParam(value = "searchValue", required = false) String searchValue,
                                                            @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getapiListByDistance(page -1, size, Latitude, Longitude, description, searchKeyword,searchValue,memberDetails.getMember());
    }


}