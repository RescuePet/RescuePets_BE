package hanghae99.rescuepets.memberpet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MissingPosterRequestDto {
    private MultipartFile postPoster;
}
