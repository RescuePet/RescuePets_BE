package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ResponseReportDto {
    private Long Id;
    private Long PetPostCatchId;
    private Long PetPostMissingId;
    private Long CommentId;
    private Long MemberId;
    private Long RespondentId;
    private String Content;
    private int Count;
    private String MemberRoleEnum;
    private String Reportcode;
    public static ResponseReportDto of(Report report) {
        return ResponseReportDto.builder()
                .Id(report.getId())
                .PetPostCatchId(Optional.ofNullable(report.getPetPostCatch()).map(PetPostCatch::getId).orElse(null))
                .PetPostMissingId(Optional.ofNullable(report.getPetPostMissing()).map(PetPostMissing::getId).orElse(null))
                .CommentId(Optional.ofNullable(report.getComment()).map(Comment::getId).orElse(null))
                .MemberId(Optional.ofNullable(report.getMember()).map(Member::getId).orElse(null))
                .RespondentId(Optional.ofNullable(report.getRespondent()).map(Member::getId).orElse(null))
                .Content(report.getContent())
                .Count(report.getCount())
                .MemberRoleEnum(report.getMember().getMemberRoleEnum().getMemberRole())
                .Reportcode(report.getReportcode())
                .build();
    }
}
