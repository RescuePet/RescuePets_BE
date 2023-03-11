package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String password;

    private String email;
}
