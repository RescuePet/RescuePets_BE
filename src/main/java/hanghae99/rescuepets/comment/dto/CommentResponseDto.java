package hanghae99.rescuepets.comment.dto;

import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userNickName;
    private String createdAt;
    private String modifiedAt;
    private Long petPostCatchId;
    private Long petPostMissingId;
    private String profileImage;

    public CommentResponseDto(Comment comment, PetPostCatch petPostCatch) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.petPostCatchId = petPostCatch.getId();
        this.profileImage = comment.getMember().getProfileImage();
    }
    public CommentResponseDto(Comment comment, PetPostMissing petPostMissing) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.petPostMissingId = petPostMissing.getId();
        this.profileImage = comment.getMember().getProfileImage();
    }

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickName = comment.getMember().getNickname();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.profileImage = comment.getMember().getProfileImage();
        if (comment.getPetPostCatch() != null) {
            this.petPostCatchId = comment.getPetPostCatch().getId();
        }
        if (comment.getPetPostMissing() != null) {
            this.petPostMissingId = comment.getPetPostMissing().getId();
        }
    }
}
