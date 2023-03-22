package hanghae99.rescuepets.declare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportIdRequestDto {
    private Long petPostMissingId;

    private Long PetPostCatchId;

    private Long CommentId;
}
