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
public class PostCoordinatesResponseDto {
    private Long memberId;
    private Long linkedPostId;
    private String linkedPostLongitude;
    private String linkedPostLatitude;
    private boolean isLinkedByMe = false;

    public static PostCoordinatesResponseDto of (PostLink postLink, Post linkedPost) {
        return PostCoordinatesResponseDto.builder()
                .memberId(postLink.getMember().getId())
                .linkedPostId(postLink.getLinkedPostId())
                .linkedPostLongitude(linkedPost.getHappenLongitude())
                .linkedPostLatitude(linkedPost.getHappenLatitude())
                .build();
    }
    public void setLinkedByMe(boolean isLinkedByMe) {
        this.isLinkedByMe = isLinkedByMe;
    }
}
