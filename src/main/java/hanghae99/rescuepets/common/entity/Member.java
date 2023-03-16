package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private MemberRoleEnum role;
    @Column
    private Long kakaoId;


    @Builder
    public Member(String email, String nickname , String password , MemberRoleEnum memberRoleEnum, Long id){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = memberRoleEnum;
    }

    @Builder
    public Member(String email, String nickname , String password , MemberRoleEnum memberRoleEnum, Long id, String address){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = memberRoleEnum;
        this.address = address;
    }

    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public Member(String nickname, String encodedPassword, String email, Long kakaoId) {
        this.nickname = nickname;
        this.password = encodedPassword;
        this.email = email;
        this.kakaoId = kakaoId;
    }
}
