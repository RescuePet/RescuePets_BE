package hanghae99.rescuepets.memberpet.dto;

import java.util.List;
import hanghae99.rescuepets.common.entity.NeuterEnum;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import hanghae99.rescuepets.common.entity.SexEnum;
import hanghae99.rescuepets.common.entity.UpkindEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class PostRequestDto {
    @NotNull(message = "게시판 타입을 선택해주세요")
    @Pattern(
            regexp = "(CATCH|MISSING)",
            message = "유효하지 않은 동물 종류입니다."
    )
    private PostTypeEnum postType;
    @NotNull(message = "동물 종류를 선택해주세요.")
    @Pattern(
            regexp = "(CAT|DOG|ETC)",
            message = "유효하지 않은 동물 종류입니다."
    )
    private UpkindEnum upkind;
    @NotNull(message = "성별을 선택해주세요.")
    @Pattern(
            regexp = "(MALE|FEMALE|UNKNOWN)",
            message = "유효하지 않은 성별입니다."
    )
    private SexEnum sexCd;
    @NotNull(message = "중성화 여부를 선택해주세요")
    @Pattern(
            regexp = "(YES|NO|UNKNOWN)",
            message = "유효하지 않은 중성화 여부입니다."
    )
    private NeuterEnum neuterYn;
    @Pattern(regexp = "^[가-힣]{2,15}$", message = "한글 2자에서 15자 사이로 입력해주세요.")
    private String kindCd;
    @Pattern(regexp = "^[가-힣]{2,8}$", message = "한글 2자에서 8자 사이로 입력해주세요.")
    private String petName;
    @Pattern(regexp = "^[0-9]{1,3}$", message = "숫자 3자리 이하로 입력해주세요.")
    private String age;
    @Max(value = 1000, message = "1000 이하의 값을 입력해주세요.")
    private String weight;
    @Pattern(regexp = "^[가-힣]{2,8}$", message = "한글 2자에서 8자 사이로 입력해주세요.")
    private String colorCd;
    @Pattern(regexp = "^([가-힣]{2,4} [가-힣0-9\\-\\s]{2,})$", message = "해당 주소는 유효하지 않습니다. 다른 주소를 선택해주세요.")
    private String happenPlace;
    @DecimalMin(value = "-180", message = "유효하지 않은 경도값입니다.")
    @DecimalMax(value = "180", message = "유효하지 않은 경도값입니다.")
    private String happenLongitude;
    @DecimalMin(value = "-90", message = "유효하지 않은 위도값입니다.")
    @DecimalMax(value = "90", message = "유효하지 않은 위도값입니다.")
    private String happenLatitude;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private String happenDt;
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "시간 형식이 잘못되었습니다. (예: 12:00, 21:30)")
    private String happenHour;
    @Size(max = 50, message = "최대 50자까지 입력 가능합니다.")
    private String specialMark;
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String content;
    @Digits(integer = 7, fraction = 0, message = "숫자만 입력 가능합니다.")
    private String gratuity;
    @Pattern(regexp = "^[0-9]{11}$", message = "11자리 숫자만 입력 가능합니다")
    private String contact;
    @NotNull(message = "openNickname 필드는 반드시 입력되어야 합니다.")
    private Boolean openNickname = true;
    @NotNull(message = "반려동물의 사진을 업로드해주세요")
    private List<MultipartFile> postImages;
}
