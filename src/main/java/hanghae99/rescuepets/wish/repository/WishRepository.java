package hanghae99.rescuepets.wish.repository;

import hanghae99.rescuepets.common.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findWishByPetPostCatchIdAndMemberId(Long postId, Long memberId);

    void deleteWishByPetPostCatchIdAndMemberId(Long postId, Long memberId);

    Optional<Wish> findWishByPetPostMissingIdAndMemberId(Long postId, Long memberId);

    void deleteWishByPetPostMissingIdAndMemberId(Long postId, Long memberId);
}
