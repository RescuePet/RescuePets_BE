package hanghae99.rescuepets.comment.dto;

import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentControllerResponse {
    private Member receiver;
    private Comment comment;
}
