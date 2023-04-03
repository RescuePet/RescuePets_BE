package hanghae99.rescuepets.report.repository;

import hanghae99.rescuepets.common.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByMember_IdAndComment_Id(Long memberId, Long commentId);

    Optional<Report> findByInformant_IdAndRespondent_Id(Long informantId, Long respondentId);

    Optional<Report> findByMember_IdAndPetPostCatch_Id(Long id, Long id1);

    Optional<Report> findByMemberIdAndPetPostMissingId(Long memberId, Long petPostMissingId);

    List<Report> findByPetPostMissing_Id(Long id);

    List<Report> findByPetPostCatchId(Long id);

    List<Report> findByCommentId(Long id);
}
