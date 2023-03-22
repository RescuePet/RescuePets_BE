package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostLinkRequestDto {
    private PetPostCatch petPostCatch;
    private PetPostMissing petPostMissing;
    private PostTypeEnum postType;
    private Long linkedPostId;
    private Member member;

    public PostLinkRequestDto(PetPostCatch petPostCatch, PetPostMissing petPostMissing, PostTypeEnum postType, Long linkedPostId, Member member){
        this.petPostCatch = petPostCatch;
        this.petPostMissing = petPostMissing;
        this.postType = postType;
        this.linkedPostId = linkedPostId;
        this.member = member;
    }
}
