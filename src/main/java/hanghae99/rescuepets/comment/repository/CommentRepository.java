package hanghae99.rescuepets.comment.repository;

import hanghae99.rescuepets.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findTop10ByMemberIdOrderByCreatedAtDesc(Long memberId);
    List<Comment> findTop50ByOrderByCreatedAtDesc();
    Page<Comment> findAllByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
    Page<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
    Integer countByPostId(Long postId);
    Integer countByMemberId(Long memberId);
}