package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByAccuserNicknameAndRespondentId(String nickname, Long respondentId);
    Optional<Report> findByAccuserNicknameAndPostId(String nickname, Long postId);
    Optional<Report> findByAccuserNicknameAndCommentId(String nickname, Long commentId);
    Integer countByRespondent(Member respondent);
    List<Report> findByPostId(Long postId);

    List<Report> findByCommentId(Long commentId);

    List<Report> findByRespondentId(Long respondentId);
}
