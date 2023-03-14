//package hanghae99.rescuepets.comment.repository;
//
//import hanghae99.rescuepets.common.entity.Comment;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findAllByUserId(Long userId);
//    List<Comment> findAllByPostId(Long postId);
//}