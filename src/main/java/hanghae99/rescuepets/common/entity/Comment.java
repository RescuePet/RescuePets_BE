package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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
//
//    @Nullable
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_post_catch_id", nullable = false)
//    private PetPostCatch petPostCatch;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_post_missing_id", nullable = false)
    private PetPostMissing petPostMissing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

//    public Comment(String content, PetPostCatch petPostCatch, Member member) {
//        this.content = content;
//        this.petPostCatch = petPostCatch;
//        this.member = member;
//    }
    public Comment(String content, PetPostMissing petPostMissing, Member member) {
        this.content = content;
        this.petPostMissing = petPostMissing;
        this.member = member;
    }

    public void update(String content) {
        this.content = content;
    }
}
