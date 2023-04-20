package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetInfoInquiryRepository extends JpaRepository<PetInfoInquiry, Long> {
    Optional<PetInfoInquiry> findByMemberIdAndDesertionNo(Long memberId, Long desertionNo);
    Integer countByDesertionNo(Long desertionNo);
}
