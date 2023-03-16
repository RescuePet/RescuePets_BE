package hanghae99.rescuepets.memberpet.repository;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PetPostCatchRepository extends JpaRepository<PetPostCatch, Long>{
    List<PetPostCatch> findAllByMemberId(Long memberId);
    Page<PetPostCatch> findByMemberId(Long memberId, Pageable pageable);
}
