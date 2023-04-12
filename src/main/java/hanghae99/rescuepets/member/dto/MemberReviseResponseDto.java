package hanghae99.rescuepets.member.dto;

import hanghae99.rescuepets.common.entity.Member;
import lombok.Getter;

@Getter
public class MemberReviseResponseDto {
    private String nickname;

    private String profileImage;

    private String email;

    private String memberRole;


    public MemberReviseResponseDto(Member member){
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
        this.email = member.getEmail();
        this.memberRole = member.getMemberRoleEnum().toString();
    }
}

