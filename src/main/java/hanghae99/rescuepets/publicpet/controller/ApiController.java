package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.PetStateEnum;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.publicpet.service.ApiDataService;
import hanghae99.rescuepets.publicpet.service.ApiScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Tag(name = "TEST용 유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class ApiController {
    private final ApiDataService apiDataService;
    private final ApiScheduler apiScheduler;
    @SecurityRequirements
    @PostMapping("api-compare-data/{pageNo}")
    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 비교 최신화", description = "자세한 설명")
    public ResponseEntity<ResponseDto> apiCompareData(@PathVariable(value = "pageNo") String pageNo,
                                                      @RequestParam(value = "state") PetStateEnum state,
                                                      @RequestParam(value = "size") String size) throws IOException {
        return apiDataService.apiCompareData(pageNo, state, size);
    }



}
