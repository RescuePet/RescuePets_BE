package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostCatchId", nullable = true)
    private PetPostCatch petPostCatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissingId", nullable = true)
    private PetPostMissing petPostMissing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    public Comment(String content, PetPostCatch petPostCatch, Member member) {
        this.content = content;
        this.petPostCatch = petPostCatch;
        this.member = member;
    }
    public Comment(String content, PetPostMissing petPostMissing, Member member) {
        this.content = content;
        this.petPostMissing = petPostMissing;
        this.member = member;
    }

    public void update(String content) {
        this.content = content;
    }
}
