package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCatchReportRepository extends JpaRepository<Report,Long> {
    Optional<Report> findByMember_IdAndPetPostCatch_Id(Long id, Long id1);



}
