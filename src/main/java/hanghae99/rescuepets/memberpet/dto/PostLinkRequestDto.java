package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class PostLinkRequestDto {
    @Pattern(regexp = "^[1-9]\\d*$", message = "양수만 입력 가능합니다.")
    private Long linkedPostId;
    public PostLinkRequestDto(Long linkedPostId){
        this.linkedPostId = linkedPostId;
    }
}