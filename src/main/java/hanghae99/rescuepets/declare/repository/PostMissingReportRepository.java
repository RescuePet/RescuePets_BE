package hanghae99.rescuepets.declare.repository;

import hanghae99.rescuepets.common.entity.ReportMissing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostMissingReportRepository extends JpaRepository<ReportMissing, Long> {
    Optional<ReportMissing> findByMemberIdAndPetPostMissingId(Long memberId, Long petPostMissingId);

}
