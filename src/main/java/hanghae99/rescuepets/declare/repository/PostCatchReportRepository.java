package hanghae99.rescuepets.declare.repository;

import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.ReportCatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCatchReportRepository extends JpaRepository<ReportCatch,Long> {
    Optional<ReportCatch> findByMember_IdAndPetPostCatch_Id(Long id, Long id1);



}
