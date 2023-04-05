package hanghae99.rescuepets.comment.repository;

import hanghae99.rescuepets.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemberId(Long memberId);
    Page<Comment> findAllByMemberId(Long memberId, Pageable pageable);
    List<Comment> findAllByPostId(Long postId);
    Integer countByMemberId(Long memberId);
}