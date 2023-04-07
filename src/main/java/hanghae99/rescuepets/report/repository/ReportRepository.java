package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByMemberIdAndCommentId(Long memberId, Long commentId);

    Optional<Report> findByMemberIdAndRespondentId(Long memberId, Long respondentId);

    Optional<Report> findByMemberIdAndPostId(Long memberId, Long postId);

    List<Report> findByPostId(Long postId);

    List<Report> findByCommentId(Long commentId);

    List<Report> findByRespondentId(Long respondentId);
}
