package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class PostLinkRequestDto {
    @Nullable
    private Long petPostCatchId;
    @Nullable
    private Long petPostMissingId;
    private PostTypeEnum postType;
    private Long linkedPostId;

    public PostLinkRequestDto(PostTypeEnum postType, Long linkedPostId){
        this.postType = postType;
        this.linkedPostId = linkedPostId;
    }
}
