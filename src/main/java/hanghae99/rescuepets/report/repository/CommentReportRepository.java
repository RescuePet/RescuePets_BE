package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentReportRepository extends JpaRepository<ReportComment, Long> {
    Optional<ReportComment> findByMember_IdAndComment_Id(Long memberId, Long commentId);

}
