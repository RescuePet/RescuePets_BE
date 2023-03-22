package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLinkMTMRequestDto {

    private Member member;
    private Long petPostMissingSlotAId;
    private Long petPostMissingSlotBId;
}

