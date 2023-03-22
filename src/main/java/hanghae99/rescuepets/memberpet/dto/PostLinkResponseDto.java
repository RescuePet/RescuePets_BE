package hanghae99.rescuepets.memberpet.dto;

import java.util.ArrayList;
import java.util.List;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PostLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostLinkResponseDto {
    private String nickName;
    private Long petPostCatchSlotAId;
    private Long petPostMissingSlotAId;
    private Long petPostCatchSlotBId;
    private Long petPostMissingSlotBId;
    private boolean isLinked = false;

    public static PostLinkResponseDto of (PostLink postLink, Member member) {
        return PostLinkResponseDto.builder()
                .nickName(member.getNickname())
                .petPostCatchSlotAId(postLink.getPetPostCatchSlotA().getId())
                .petPostMissingSlotAId(postLink.getPetPostMissingSlotA().getId())
                .petPostCatchSlotBId(postLink.getPetPostCatchSlotB().getId())
                .petPostMissingSlotBId(postLink.getPetPostMissingSlotB().getId())
                .build();
    }

    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }
}
