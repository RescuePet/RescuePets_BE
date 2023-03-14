package hanghae99.rescuepets.common.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private PetPostCatch catchPost;

    @ManyToOne
    private PetPostMissing missingPost;

    public Wish(Member member, PetPostCatch postCatch) {
        this.member = member;
        this.catchPost = postCatch;
    }

    public Wish(Member member, PetPostMissing postMissing) {
        this.member = member;
        this.missingPost = postMissing;
    }
}
