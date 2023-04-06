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
    @PostMapping(value = "/petMissing")
    public ResponseEntity<ResponseDto> reportPetPostMissing(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissing(reportRequestDto, userDetails.getMember());
    }

    @PutMapping(value = "/petMissing")
    public ResponseEntity<ResponseDto> reportPetPostMissingPut(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissingPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/petMissing")
    public ResponseEntity<ResponseDto> reportPetPostMissingDelete(@RequestBody ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMissingDelete(reportIdRequestDto,userDetails.getMember());
    }


    @PostMapping(value = "/petcatch")
    public ResponseEntity<ResponseDto> reportPetPostCatch(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatch(reportRequestDto, userDetails.getMember());
    }

    @PutMapping(value = "/petcatch")
    public ResponseEntity<ResponseDto> reportPetPostCatchPut(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatchPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/petcatch")
    public ResponseEntity<ResponseDto> reportPetPostCatchDelete(@RequestBody ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCatchDelete(reportIdRequestDto,userDetails.getMember());
    }


    @PostMapping(value = "/comment")
    public ResponseEntity<ResponseDto> reportComment(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return  reportService.reportComment(reportRequestDto,userDetails.getMember());
    }

    @PutMapping(value = "/comment")
    public ResponseEntity<ResponseDto> reportCommentPut(@RequestBody ReportRequestDto reportRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCommentPut(reportRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/comment")
    public ResponseEntity<ResponseDto> reportCommentDelete(@RequestBody ReportIdRequestDto reportIdRequestDto, @Parameter(hidden = true)@AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportCommentDelete(reportIdRequestDto, userDetails.getMember());
    }

    @PostMapping(value = "/member")
    public ResponseEntity<ResponseDto> reportMember(@RequestBody ReportMemberRequestDto reportMemberRequestDto,  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMember(reportMemberRequestDto,userDetails.getMember());
    }

    @PutMapping(value = "/member")
    public ResponseEntity<ResponseDto> reporMemberPut(@RequestBody ReportMemberRequestDto reportMemberRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reporMemberPut(reportMemberRequestDto, userDetails.getMember());
    }

    @DeleteMapping(value = "/member")
    public ResponseEntity<ResponseDto> reportMemberDelete(@RequestBody ReportMemberRequestDto reportMemberRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails userDetails){
        return reportService.reportMemberDelete(reportMemberRequestDto, userDetails.getMember());
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
