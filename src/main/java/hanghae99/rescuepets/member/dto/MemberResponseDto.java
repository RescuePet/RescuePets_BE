package hanghae99.rescuepets.member.dto;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.memberpet.dto.PostImageResponseDto;
import hanghae99.rescuepets.memberpet.dto.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String nickname;

    private String email;

    private String profileImage;

    private String memberRole;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.memberRole = member.getMemberRoleEnum().toString();
    }

}
