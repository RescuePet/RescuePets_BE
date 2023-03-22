package hanghae99.rescuepets.memberpet.dto;

import java.util.ArrayList;
import java.util.List;

import hanghae99.rescuepets.common.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostLinkResponseDto {
    private Member member;
    private Long petPostCatchId;
    private Long petPostMissingId;
    private PostTypeEnum postType;
    private Long linkedPostId;
    private boolean isLinkedByMe = false;

    public static PostLinkResponseDto of (PostLink postLink, Member member) {
        return PostLinkResponseDto.builder()
                .petPostCatchId(postLink.getPetPostCatch().getId())
                .petPostMissingId(postLink.getPetPostMissing().getId())
                .postType(postLink.getPostType())
                .linkedPostId(postLink.getLinkedPostId())
                .member(member)
                .build();
    }

    public void setLinkedByMe(boolean isLinkedByMe) {
        this.isLinkedByMe = isLinkedByMe;
    }
}
