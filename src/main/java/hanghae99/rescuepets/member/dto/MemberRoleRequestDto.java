package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRoleRequestDto {
    private Long memberId;
    private String memberRole;
}
