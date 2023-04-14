package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ReportResponseDto {
    private Long id;
    private String accuseMemberNickname;
    private String reportType;
    private String reportReason;
    private String respondentNickname;
    private String respondentRole;
    private Long respondentId;
    private Long postId;
    private Long commentId;
    private String content;
    private int reportCount;
    public static ReportResponseDto of(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .accuseMemberNickname(report.getAccuserNickname())
                .reportType(report.getReportTypeEnum().getValue())
                .reportReason(report.getReportReasonEnum().getValue())
                .respondentNickname(report.getRespondentNickname())
                .respondentId(Optional.ofNullable(report.getRespondent()).map(Member::getId).orElse(null))
                .postId(Optional.ofNullable(report.getPost()).map(Post::getId).orElse(null))
                .commentId(Optional.ofNullable(report.getComment()).map(Comment::getId).orElse(null))
                .content(report.getContent())
                .build();
    }
    public void setRespondentRole(String respondentRole){
        this.respondentRole = respondentRole;
    }
    public void setReportCount(int reportCount){
        this.reportCount = reportCount;
    }
}
