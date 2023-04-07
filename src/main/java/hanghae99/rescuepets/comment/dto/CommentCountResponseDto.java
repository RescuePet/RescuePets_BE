package hanghae99.rescuepets.comment.dto;

import lombok.Getter;

@Getter
public class CommentCountResponseDto {
    private int commentCount;

    public CommentCountResponseDto(int commentCount) {
        this.commentCount = commentCount;
    }
}
