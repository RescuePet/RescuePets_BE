package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Scrap extends TimeStamped{

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petInfoByAPIdesertionNo", referencedColumnName = "desertionNo")
    private PetInfoByAPI petInfoByAPI;

    public void setPetPostCatch(PetPostCatch petPostCatch) {
        this.petPostCatch = petPostCatch;
    }

    public Scrap(Member member, PetPostMissing petPostMissing) {
        this.member = member;
        this.petPostMissing = petPostMissing;
    }

    public Scrap(Member member, PetPostCatch petPostCatch) {
        this.member = member;
        this.petPostCatch = petPostCatch;
    }

    public Scrap(Member member, PetInfoByAPI petInfoByAPI) {
        this.member = member;
        this.petInfoByAPI = petInfoByAPI;
    }
}