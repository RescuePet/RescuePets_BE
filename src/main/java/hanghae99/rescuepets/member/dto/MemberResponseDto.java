package hanghae99.rescuepets.member.dto;

import hanghae99.rescuepets.common.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String nickname;

    private String email;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }

}
