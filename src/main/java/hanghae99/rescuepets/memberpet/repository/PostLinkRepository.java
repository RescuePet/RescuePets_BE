package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLinkRepository extends JpaRepository<PostLink, Long> {
    List<PostLink> findAllByPetPostCatch(PetPostCatch petPostCatch);
    List<PostLink> findAllByPetPostCatchAndMemberId(PetPostCatch petPostCatch, Long memberId);
    void deleteByPetPostCatchAndMemberId(PetPostCatch petPostCatch, Long memberId);
    void deleteByPetPostMissingAndMemberId(PetPostMissing petPostMissing, Long memberId);
    void deleteByPetPostCatchAndMemberIdAndPostTypeAndLinkedPostId
            (PetPostCatch petPostCatch, Long memberId, PostTypeEnum postType, Long linkedPostId);
    void deleteByPetPostMissingAndMemberIdAndPostTypeAndLinkedPostId
            (PetPostMissing petPostMissing, Long memberId, PostTypeEnum postType, Long linkedPostId);
}
