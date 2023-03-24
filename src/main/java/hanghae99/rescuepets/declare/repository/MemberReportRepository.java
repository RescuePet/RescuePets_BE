package hanghae99.rescuepets.declare.repository;

import hanghae99.rescuepets.common.entity.ReportMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberReportRepository extends JpaRepository<ReportMember, Long> {
    Optional<ReportMember> findByInformant_IdAndRespondent_Id(Long informantId, Long respondentId);

}
