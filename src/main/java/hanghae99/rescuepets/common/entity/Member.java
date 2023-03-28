package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static hanghae99.rescuepets.common.entity.MemberRoleEnum.MEMBER;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String password;
    @Column
    private Long kakaoId;
    @Column
    private String profileImage;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum memberRole = MEMBER;
    @Builder
    public Member(Long id, String email, String nickname , String password, String address, Long kakaoId, String profileImage){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.kakaoId = kakaoId;
        this.profileImage = profileImage;
    }

    public void setKakao(Long kakaoId, String profileImage) {
        this.kakaoId = kakaoId;
        this.profileImage = profileImage;
    }

    public void withdrawal() {
        this.nickname = "탈퇴한 회원";
        this.email = "탈퇴한 회원";
        this.address = "탈퇴한 회원";
        this.kakaoId = null;
        this.profileImage = null;
    }
}
