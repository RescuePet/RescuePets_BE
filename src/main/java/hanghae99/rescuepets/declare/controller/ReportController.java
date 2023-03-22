package hanghae99.rescuepets.declare.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.ReportEnum;
import hanghae99.rescuepets.common.entity.ReportMember;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.declare.dto.ReportIdRequestDto;
import hanghae99.rescuepets.declare.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.declare.dto.ReportRequestDto;
import hanghae99.rescuepets.declare.service.ReportService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;


@RequestMapping("/api/declare")
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    @PostMapping(value = "/petMissing",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostMissing(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissing(reportRequestDto, userDetails.getMember());
    }

    @PutMapping(value = "/petMissing",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostMissingPut(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissingPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/petMissing",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostMissingDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissingDelete(reportIdRequestDto,userDetails.getMember());
    }


    @PostMapping(value = "/petcatch",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostCatch(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatch(reportRequestDto, userDetails.getMember());
    }

    @PutMapping(value = "/petcatch",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostCatchPut(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatchPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/petcatch",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportPetPostCatchDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatchDelete(reportIdRequestDto,userDetails.getMember());
    }


    @PostMapping(value = "/comment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportComment(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return  reportService.reportComment(reportRequestDto,userDetails.getMember());
    }

    @PutMapping(value = "/comment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportCommentPut(@ModelAttribute ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCommentPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/comment",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportCommentDelete(@ModelAttribute ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true)@AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCommentDelete(reportIdRequestDto, userDetails.getMember());
    }

    @PostMapping(value = "/member",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> reportMember(@ModelAttribute ReportMemberRequestDto reportMemberRequestDto,  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMember(reportMemberRequestDto,userDetails.getMember());
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
