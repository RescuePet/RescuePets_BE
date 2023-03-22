package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.PostLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLinkRepository extends JpaRepository<PostLink, Long> {
    List<PostLink> findAllByPetPostCatchSlotA(PetPostCatch petPostCatchSlotA);
    List<PostLink> findAllByPetPostMissingSlotA(PetPostMissing petPostMissingSlotA);
    List<PostLink> findAllByPetPostCatchSlotB(PetPostCatch petPostCatchSlotB);
    List<PostLink> findAllByPetPostMissingSlotB(PetPostMissing petPostMissingSlotB);
    List<PostLink> findAllByPetPostCatchSlotAAndMemberId(PetPostCatch petPostCatchSlotA, Member member);
    List<PostLink> findAllByPetPostMissingSlotAAndMemberId(PetPostMissing petPostMissingSlotA, Member member);
    List<PostLink> findAllByPetPostCatchSlotBAndMemberId(PetPostCatch petPostCatchSlotB, Member member);
    List<PostLink> findAllByPetPostMissingSlotBAndMemberId(PetPostMissing petPostMissingSlotB, Member member);
    void deleteByPetPostCatchSlotAAndMemberId(PetPostCatch petPostCatchSlotA, Member member);
    void deleteByPetPostMissingSlotAAndMemberId(PetPostCatch petPostMissingSlotA, Member member);
    void deleteByPetPostCatchSlotBAndMemberId(PetPostCatch petPostCatchSlotB, Member member);
    void deleteByPetPostMissingSlotBAndMemberId(PetPostCatch petPostMissingSlotB, Member member);
}
