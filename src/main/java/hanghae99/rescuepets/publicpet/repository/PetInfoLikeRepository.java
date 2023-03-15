package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetInfoLikeRepository extends JpaRepository<PetInfoLike, Long> {
    Optional<PetInfoLike> findByMemberIdAndDesertionNo(Long memberId, String desertionNo);
    Optional<PetInfoLike> findByMemberIdAndId(Long memberId, Long petInfoLikeId);
    void deleteByMemberIdAndId(Long memberId, Long PetInfoLikeId);
}
