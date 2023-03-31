package hanghae99.rescuepets.scrap.repository;

import hanghae99.rescuepets.common.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findScrapByPetPostCatchIdAndMemberId(Long postId, Long memberId);
    Optional<Scrap> findScrapByPetPostMissingIdAndMemberId(Long postId, Long memberId);
    Optional<Scrap> findByMemberIdAndPetInfoByAPI_desertionNo(Long memberId, String desertionNo);
    Optional<Scrap> findByMemberId(Long memberId);

    Integer countByPetPostCatchId(Long postId);
    Integer countByPetPostMissingId(Long postId);
    Integer countByPetInfoByAPI_desertionNo(String desertionNo);
    Integer countByMemberId(Long memberId);

    Page<Scrap> findByMemberId(Long memberId, Pageable pageable);

    void deleteScrapByPetPostCatchIdAndMemberId(Long postId, Long memberId);
    void deleteScrapByPetPostMissingIdAndMemberId(Long postId, Long memberId);
    void deleteByMemberIdAndPetInfoByAPI_desertionNo(Long memberId, String desertionNo);
}
