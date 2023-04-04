package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeResponseDto {
    String Timelim;

    public TimeResponseDto(String Timelim){
        this.Timelim = Timelim;
    }
}
