package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostImageResponseDto {
    private Long id;
    private String imageURL;

    public static PostImageResponseDto of (PostImage postImage) {
        return PostImageResponseDto.builder()
                .id(postImage.getId())
                .imageURL(postImage.getImageURL())
                .build();
    }
}
