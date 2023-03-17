package hanghae99.rescuepets.member.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter

public class SignupRequestDto {
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*?[a-zA-Z])(?=.*?[\\d])(?=.*?[~!@#$%^&*()_+=\\-`]).{8,30}")
    private String password;

    @Email
    private String email;

    @NotNull
    private String nickname;

}
