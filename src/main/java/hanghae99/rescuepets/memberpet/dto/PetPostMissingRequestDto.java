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
    private String happenPlace;
    private String kindCd;
    private String specialMark;
    private String content;
    @Nullable
    private MultipartFile popfile;
}
