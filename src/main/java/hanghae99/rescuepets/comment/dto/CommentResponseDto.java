//package hanghae99.rescuepets.comment.dto;
//
//import hanghae99.rescuepets.common.entity.Comment;
//import hanghae99.rescuepets.common.entity.Member;
//import lombok.Getter;
//
//@Getter
//public class CommentResponseDto {
//    private Long id;
//    private String content;
//    private String userNickName;
//    private String createdAt;
//    private String modifiedAt;
//    private Long petPostMissingId;
//
//    public CommentResponseDto(Comment comment) {
//        this.id = comment.getId();
//        this.content = comment.getContent();
//        this.userNickName = comment.getMember().getNickname();
//        this.createdAt = comment.getCreatedAt().toString();
//        this.modifiedAt = comment.getModifiedAt().toString();
//        this.petPostMissingId = comment.getPetPostMissing().getId();
//    }
//
//    public CommentResponseDto(Comment comment, Member member) {
//        this.id = comment.getId();
//        this.content = comment.getContent();
//        this.userNickName = member.getNickname();
//        this.createdAt = comment.getCreatedAt().toString();
//        this.modifiedAt = comment.getModifiedAt().toString();
//        this.petPostMissingId = comment.getPetPostMissing().getId();
//    }
//}
