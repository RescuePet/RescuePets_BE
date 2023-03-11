package hanghae99.rescuepets.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter
@Entity
@NoArgsConstructor

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String address;
    private String password;
    private MemberRoleEnum role;


    @Builder
    public Member (String email, String nickname ,String password ,MemberRoleEnum memberRoleEnum,Long id){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = memberRoleEnum;
    }
}
