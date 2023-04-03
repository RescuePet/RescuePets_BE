package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Boolean stop;

    @Column
    private LocalDateTime reportdate;

    @Column
    private String profileImage;

    @Builder
    public Member(Long id, String email, String nickname , String password, String address, Long kakaoId, String profileImage){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.kakaoId = kakaoId;
        this.profileImage = profileImage;
        this.stop = false;
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

    public void updateImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void Stop(LocalDateTime localDateTime){
        this.stop = true;
        this.reportdate = localDateTime;
    }

    public void Start(){
        this.stop = false;
    }
}
