package hanghae99.rescuepets.common.entity;

import javax.persistence.*;
import java.util.Set;

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String address;
    private MemberRoleEnum role;
}
