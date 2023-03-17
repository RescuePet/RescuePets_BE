package hanghae99.rescuepets.memberpet.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;

@Getter
@Setter
public class PetPostCatchRequestDto {

    private String upkind;
    private String kindCd;
    private String sexCd;
    private String neuterYn;
    private String age;
    private String weight;
    private String colorCd;
    private String happenPlace;
    private String happenDt;
    private String happenHour;
    private String specialMark;
    private String content;
    private Boolean openNickname;
    @Nullable
    private List<MultipartFile> postImages;
}
