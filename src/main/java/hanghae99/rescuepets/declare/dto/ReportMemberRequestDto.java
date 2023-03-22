package hanghae99.rescuepets.declare.dto;

import hanghae99.rescuepets.common.entity.ReportEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMemberRequestDto {
    private String content;
    private Long informantId;
    private ReportEnum reportCode;

}
