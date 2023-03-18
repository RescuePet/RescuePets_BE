package hanghae99.rescuepets.memberpet.repository;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetPostMissingRepository extends JpaRepository<PetPostMissing, Long>{
    List<PetPostMissing> findAllByMemberId(Long memberId);
    Page<PetPostMissing> findByMemberId(Long memberId, Pageable pageable);
}