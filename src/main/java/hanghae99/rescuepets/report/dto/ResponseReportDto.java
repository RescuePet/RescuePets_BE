package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ResponseReportDto {
    private Long Id;
    private Long InformantId;
    private Long PetPostCatchId;
    private Long PetPostMissingId;
    private Long CommentId;
    private Long MemberId;
    private Long RespondentId;
    private String Content;
    private int Count;
    private String Reportcode;
    public static ResponseReportDto of(Report report) {
        return ResponseReportDto.builder()
                .Id(report.getId())
                .InformantId(Optional.ofNullable(report.getInformant()).map(Member::getId).orElse(null))
                .PetPostCatchId(Optional.ofNullable(report.getPetPostCatch()).map(PetPostCatch::getId).orElse(null))
                .PetPostMissingId(Optional.ofNullable(report.getPetPostMissing()).map(PetPostMissing::getId).orElse(null))
                .CommentId(Optional.ofNullable(report.getComment()).map(Comment::getId).orElse(null))
                .MemberId(Optional.ofNullable(report.getMember()).map(Member::getId).orElse(null))
                .RespondentId(Optional.ofNullable(report.getRespondent()).map(Member::getId).orElse(null))
                .Content(report.getContent())
                .Count(report.getCount())
                .Reportcode(report.getReportcode())
                .build();

    }

}
