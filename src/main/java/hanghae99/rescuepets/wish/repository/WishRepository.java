package hanghae99.rescuepets.wish.repository;

import hanghae99.rescuepets.common.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findWishByCatchPostIdAndMemberId(Long postId, Long memberId);

    void deleteWishByCatchPostIdAndMemberId(Long postId, Long memberId);

    Optional<Wish> findWishByMissingPostIdAndMemberId(Long postId, Long memberId);

    void deleteWishByMissingPostIdAndMemberId(Long postId, Long memberId);
}
