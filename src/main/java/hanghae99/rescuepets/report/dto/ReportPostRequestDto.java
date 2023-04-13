package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.ReportEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportPostRequestDto {
    private String content;
    private Long postId;
    private ReportEnum reportEnum;
}
