package hanghae99.rescuepets.comment.dto;

import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.PostImage;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import lombok.Getter;

@Getter
public class CommentByMemberResponseDto {
    private Long id;
    private String content;
    private String userNickName;
    private String createdAt;
    private String modifiedAt;
    private Long postId;
    private String profileImage;
    private PostTypeEnum postType;
    private String postImageURL;

    public CommentByMemberResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.profileImage = comment.getMember().getProfileImage();
        this.postId = comment.getPost().getId();
        this.postType = comment.getPost().getPostType();
        this.postImageURL = comment.getPost().getPostImages().get(0).getImageURL();
    }
}
