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
    private Long memberId;
    private PostTypeEnum postType;
    private Long linkedPostId;
    private boolean isLinkedByMe = false;

    public static PostLinkResponseDto of (PostLink postLink) {
        return PostLinkResponseDto.builder()
                .postType(postLink.getPost().getPostType())
                .linkedPostId(postLink.getLinkedPostId())
                .memberId(postLink.getMember().getId())
                .build();
    }
    public void setLinkedByMe(boolean isLinkedByMe) {
        this.isLinkedByMe = isLinkedByMe;
    }
}
