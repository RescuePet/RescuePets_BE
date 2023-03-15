package hanghae99.rescuepets.memberpet.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;

@Getter
@Setter
public class PetPostCatchRequestDto {

    private String happenPlace;
    private String kindCd;
    private String specialMark;
    private String content;
    private Boolean openNickname;
    @Nullable
    private MultipartFile popfile;
}
