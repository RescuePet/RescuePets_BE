package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class LoginRequestDto {

    private String password;
    @Email
    private String email;
}
