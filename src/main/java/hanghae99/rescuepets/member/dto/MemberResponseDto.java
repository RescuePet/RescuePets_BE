package hanghae99.rescuepets.member.dto;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String nickname;

    private String email;

    private MemberRoleEnum role;

    @Builder
    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.role = member.getRole();


    }

}
