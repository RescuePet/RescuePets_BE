package hanghae99.rescuepets.report.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.ReportEnum;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.report.dto.ReportIdRequestDto;
import hanghae99.rescuepets.report.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.report.dto.ReportRequestDto;
import hanghae99.rescuepets.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;


@RequestMapping("/api/report")
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    @PostMapping(value = "/petMissing",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostMissing(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportMissing(reportRequestDto, memberDetails.getMember());
    }

    @GetMapping("/all")
    @Operation(summary = "Report 전체 게시글을 페이징없이 불러오기", description = "Report를 페이징없이 불러옵니다")
    public ResponseEntity<ResponseDto> getReportAll(@RequestParam(required = false, defaultValue = "createdAt") String sortBy, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {

        return reportService.getReportAll(sortBy, memberDetails.getMember());
    }

    @DeleteMapping(value = "/petMissing",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostMissingDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportMissingDelete(reportIdRequestDto,memberDetails.getMember());
    }


    @PostMapping(value = "/petcatch",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostCatch(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportCatch(reportRequestDto, memberDetails.getMember());
    }



    @DeleteMapping(value = "/petcatch",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostCatchDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportCatchDelete(reportIdRequestDto,memberDetails.getMember());
    }


    @PostMapping(value = "/comment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportComment(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return  reportService.reportComment(reportRequestDto,memberDetails.getMember());
    }



    @DeleteMapping(value = "/comment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportCommentDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true)@AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportCommentDelete(reportIdRequestDto, memberDetails.getMember());
    }

    @PostMapping(value = "/member",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportMember(@ModelAttribute ReportMemberRequestDto reportMemberRequestDto,  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportMember(reportMemberRequestDto,memberDetails.getMember());
    }

    @DeleteMapping(value = "/member",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportMemberDelete(@ModelAttribute ReportMemberRequestDto reportMemberRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportMemberDelete(reportMemberRequestDto, memberDetails.getMember());
    }






    // value 값과 key 값 바꾸는 로직
    public class TempEnumConverter extends PropertyEditorSupport {
        public void setAsText(final String text) throws IllegalArgumentException {
            setValue(ReportEnum.fromValue(text));
        }
    }
    // value 값과 key 값 바꾸는 로직
    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(ReportEnum.class, new TempEnumConverter());
    }

}
