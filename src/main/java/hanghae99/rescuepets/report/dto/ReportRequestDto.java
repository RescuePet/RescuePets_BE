package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.ReportEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {


    private String content;
    private Long petPostMissingId;

    private Long petPostCatchId;

    private Long CommentId;
    private ReportEnum reportCode;
}
