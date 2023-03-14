package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByPostIdAndUserId(Long postId, Long userId);
    void deletePostLikeByUserIdAndPostId(Long userId, Long postId);
}