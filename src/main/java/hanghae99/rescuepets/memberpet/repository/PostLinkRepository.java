package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.PostLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLinkRepository extends JpaRepository<PostLink, Long> {
    Optional<PostLink> findAllByPetPostCatchSlotA(PetPostCatch petPostCatchSlotA);
    Optional<PostLink> findAllByPetPostMissingSlotA(PetPostMissing petPostMissingSlotA);
    Optional<PostLink> findAllByPetPostCatchSlotB(PetPostCatch petPostCatchSlotB);
    Optional<PostLink> findAllByPetPostMissingSlotB(PetPostMissing petPostMissingSlotB);
    Optional<PostLink> findAllByPetPostCatchSlotAAndMemberId(PetPostCatch petPostCatchSlotA, Member member);
    Optional<PostLink> findAllByPetPostMissingSlotAAndMemberId(PetPostMissing petPostMissingSlotA, Member member);
    Optional<PostLink> findAllByPetPostCatchSlotBAndMemberId(PetPostCatch petPostCatchSlotB, Member member);
    Optional<PostLink> findAllByPetPostMissingSlotBAndMemberId(PetPostMissing petPostMissingSlotB, Member member);
    void deleteByPetPostCatchSlotAAndMemberId(PetPostCatch petPostCatchSlotA, Member member);
    void deleteByPetPostMissingSlotAAndMemberId(PetPostCatch petPostMissingSlotA, Member member);
    void deleteByPetPostCatchSlotBAndMemberId(PetPostCatch petPostCatchSlotB, Member member);
    void deleteByPetPostMissingSlotBAndMemberId(PetPostCatch petPostMissingSlotB, Member member);
}
