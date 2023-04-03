package hanghae99.rescuepets.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateRequestDto {
    private String nickname;

    @Nullable
    private MultipartFile image;
}
