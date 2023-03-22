package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate suspendedUntil;

    @Builder
    public Member(Long id, String email, String nickname , String password, String address, Long kakaoId){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.kakaoId = kakaoId;
    }

    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }



    public void setSuspendedUntil(LocalDate plusDays) {
        this.suspendedUntil = plusDays;
    }
}
