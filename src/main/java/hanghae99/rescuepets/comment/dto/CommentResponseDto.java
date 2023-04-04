package hanghae99.rescuepets.comment.dto;

import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userNickName;
    private String createdAt;
    private String modifiedAt;
    private Long postId;
    private String profileImage;
    private PostTypeEnum postType;

    public CommentResponseDto(Comment comment, Post post) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.postId = post.getId();
        this.profileImage = comment.getMember().getProfileImage();
    }

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.profileImage = comment.getMember().getProfileImage();
        this.postId = comment.getPost().getId();
        this.postType = comment.getPost().getPostType();
    }
}
