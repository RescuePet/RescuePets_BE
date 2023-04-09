package hanghae99.rescuepets.comment.dto;
import lombok.Getter;
import javax.validation.constraints.Size;

@Getter
public class CommentRequestDto {
    @Size(max = 200, message = "최대 200자까지 입력 가능합니다.")
    private String content;
}
