package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostLinkRequestDto {
    private Long linkedPostId;

    public PostLinkRequestDto(Long linkedPostId){
        this.linkedPostId = linkedPostId;
    }
}
