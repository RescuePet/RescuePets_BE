package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByPetPostCatchIdAndMemberId(Long petPostCatchId, Long memberId);
//    void deletePostLikeByPetPostCatchIdAndMemberId(Long memberId, Long petPostCatchId);
    void deletePostLikeByPetPostCatchIdAndMemberId(Long petPostCatchId, Long memberId);
    Optional<Wish> findByPetPostMissingIdAndMemberId(Long petPostMissingId, Long memberId);
    void deletePostLikeByPetPostMissingIdAndMemberId(Long petPostMissingId, Long memberId);
}