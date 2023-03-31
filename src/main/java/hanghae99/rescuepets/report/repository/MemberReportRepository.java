package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByInformant_IdAndRespondent_Id(Long informantId, Long respondentId);

}
