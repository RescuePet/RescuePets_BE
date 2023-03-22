package hanghae99.rescuepets.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoDto {

    private Long id;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    private String profileImage;

}
