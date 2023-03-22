package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PostLinkCTCRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_post_catch_id")
    private PetPostCatch petPostCatchSlotA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petPostMissingIdA", nullable = true)
    private PetPostMissing petPostMissingSlotA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_post_catch_id")
    private PetPostCatch petPostCatchSlotB;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petPostMissingIdB", nullable = true)
    private PetPostMissing petPostMissingSlotB;

    public PostLink(PetPostCatch petPostCatchSlotA, PetPostCatch petPostCatchSlotB, Member member) {
        this.member = member;
        this.petPostCatchSlotA = petPostCatchSlotA;
        this.petPostCatchSlotB = petPostCatchSlotB;
    }
    public PostLink(PetPostCatch petPostCatchSlotA, PetPostMissing petPostMissingSlotB, Member member) {
        this.member = member;
        this.petPostCatchSlotA = petPostCatchSlotA;
        this.petPostMissingSlotB = petPostMissingSlotB;
    }
    public PostLink(PetPostMissing petPostMissingSlotA, PetPostCatch petPostCatchSlotB, Member member) {
        this.member = member;
        this.petPostMissingSlotA = petPostMissingSlotA;
        this.petPostCatchSlotB = petPostCatchSlotB;
    }
    public PostLink(PetPostMissing petPostMissingSlotA, PetPostMissing petPostMissingSlotB, Member member) {
        this.member = member;
        this.petPostMissingSlotA = petPostMissingSlotA;
        this.petPostMissingSlotB = petPostMissingSlotB;
    }

}
