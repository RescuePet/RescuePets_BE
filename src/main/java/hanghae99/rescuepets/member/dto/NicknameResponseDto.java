package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameResponseDto {
   private String nickname;

    public NicknameResponseDto(String nickname){
       this.nickname = nickname;
   }
}
