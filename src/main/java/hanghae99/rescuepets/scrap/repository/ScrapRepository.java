package hanghae99.rescuepets.scrap.repository;

import hanghae99.rescuepets.common.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findScrapByPostIdAndMemberId(Long postId, Long memberId);
//    Optional<Scrap> findByMemberIdAndPetInfoByAPI_desertionNo(Long memberId, Long desertionNo);
    Optional<Scrap> findByMemberIdAndPetInfoByAPI_desertionNo(Long memberId, Long desertionNo);
    Optional<Scrap> findByMemberId(Long memberId);

    Integer countByPostId(Long postId);
    Integer countByPetInfoByAPI_desertionNo(Long desertionNo);
    Integer countByMemberId(Long memberId);

    Page<Scrap> findByMemberId(Long memberId, Pageable pageable);

    void deleteScrapByPostIdAndMemberId(Long postId, Long memberId);
    void deleteByMemberIdAndPetInfoByAPI_desertionNo(Long memberId, Long desertionNo);

    List<Scrap> findByPetInfoByAPI_desertionNo(Long desertionNo);
}
