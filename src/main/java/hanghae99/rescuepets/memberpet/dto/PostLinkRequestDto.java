package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLinkRequestDto {
    private PostTypeEnum postType;
    private Long linkedPostId;

}
