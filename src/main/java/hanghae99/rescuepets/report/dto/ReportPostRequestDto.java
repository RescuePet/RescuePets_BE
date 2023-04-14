package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.ReportReasonEnum;
import hanghae99.rescuepets.common.entity.ReportTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportPostRequestDto {
    private ReportReasonEnum reportReasonEnum;
    private Long postId;
    private String content;
}
