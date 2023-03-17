package hanghae99.rescuepets.memberpet.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;

@Getter
@Builder
public class PetPostMissingRequestDto {
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
    @Nullable
    private List<MultipartFile> postImages;
}
