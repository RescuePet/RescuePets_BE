package hanghae99.rescuepets.common.entity;

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
    @JoinColumn(name = "petPostCatchIdA", nullable = true)
    private PetPostCatch petPostCatchSlotA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petPostMissingIdA", nullable = true)
    private PetPostMissing petPostMissingSlotA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petPostCatchIdB", nullable = true)
    private PetPostCatch petPostCatchSlotB;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petPostMissingIdB", nullable = true)
    private PetPostMissing petPostMissingSlotB;


    @ManyToOne
    @JoinColumn(name = "pet_post_catch_id")
    private PetPostCatch petPostCatch;

    public void setPetPostCatch(PetPostCatch petPostCatch) {
        this.petPostCatch = petPostCatch;
    }
}
