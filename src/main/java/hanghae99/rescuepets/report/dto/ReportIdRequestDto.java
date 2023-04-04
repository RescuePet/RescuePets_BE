package hanghae99.rescuepets.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportIdRequestDto {
    private Long petPostMissingId;

    private Long PetPostCatchId;

    private Long CommentId;

    private Long MemberId;
}
