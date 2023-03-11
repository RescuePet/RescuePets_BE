package hanghae99.rescuepets.member.dto;

import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class EmailResponseDto {
    private String email;


    public EmailResponseDto(String email){
        this.email = email;
    }
}
