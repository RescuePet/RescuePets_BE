package hanghae99.rescuepets.report.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.ReportEnum;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.report.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.report.dto.ReportCommentRequestDto;
import hanghae99.rescuepets.report.dto.ReportPostRequestDto;
import hanghae99.rescuepets.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/post")
    public ResponseEntity<ResponseDto> reportPost(@RequestBody ReportPostRequestDto reportPostRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportPost(reportPostRequestDto, memberDetails.getMember());
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<ResponseDto> reportComment(@RequestBody ReportCommentRequestDto reportCommentRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return  reportService.reportComment(reportCommentRequestDto,memberDetails.getMember());
    }

    @PostMapping(value = "/member")
    public ResponseEntity<ResponseDto> reportMember(@RequestBody ReportMemberRequestDto reportMemberRequestDto,  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportMember(reportMemberRequestDto,memberDetails.getMember());
    }

    @GetMapping("/all")
    @Operation(summary = "Report 전체 게시글을 페이징없이 불러오기", description = "Report를 페이징없이 불러옵니다")
    public ResponseEntity<ResponseDto> getReportAll(@RequestParam(required = false, defaultValue = "createdAt") String sortBy, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {

        return reportService.getReportAll(sortBy, memberDetails.getMember());
    }

    @DeleteMapping(value = "/{reportId}")
    public ResponseEntity<ResponseDto> reportDelete(@PathVariable Long reportId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return reportService.reportDelete(reportId, memberDetails.getMember());
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




