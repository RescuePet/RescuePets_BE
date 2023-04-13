package hanghae99.rescuepets.report.dto;

import hanghae99.rescuepets.common.entity.ReportEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCommentRequestDto {
    private String content;
    private Long CommentId;
    private ReportEnum reportEnum;
}
