package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetInfoScrapRepository extends JpaRepository<PetInfoScrap, Long> {
    Optional<PetInfoScrap> findByMemberIdAndDesertionNo(Long memberId, String desertionNo);
    Integer countByDesertionNo(String desertionNo);
    void deleteByMemberIdAndDesertionNo(Long memberId, String desertionNo);
}
