package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostCatchId")
    private PetPostCatch petPostCatch;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissingId")
    private PetPostMissing petPostMissing;

    public void setPetPostCatch(PetPostCatch petPostCatch) {
        this.petPostCatch = petPostCatch;
    }

    public Wish(Member member, PetPostMissing petPostMissing) {
        this.member = member;
        this.petPostMissing = petPostMissing;
    }
}
