package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ResponseReportDto {
    private Long id;
    private Long postId;
    private Long commentId;
    private Long memberId;
    private Long respondentId;
    private String content;
    private int count;
    private String memberRoleEnum;
    private String reportcode;
    public static ResponseReportDto of(Report report) {
        return ResponseReportDto.builder()
                .id(report.getId())
                .postId(Optional.ofNullable(report.getPost()).map(Post::getId).orElse(null))
                .commentId(Optional.ofNullable(report.getComment()).map(Comment::getId).orElse(null))
                .memberId(Optional.ofNullable(report.getMember()).map(Member::getId).orElse(null))
                .respondentId(Optional.ofNullable(report.getRespondent()).map(Member::getId).orElse(null))
                .content(report.getContent())
                .count(report.getCount())
                .memberRoleEnum(report.getMember().getMemberRoleEnum().getMemberRole())
                .reportcode(report.getReportcode())
                .build();
    }
}
