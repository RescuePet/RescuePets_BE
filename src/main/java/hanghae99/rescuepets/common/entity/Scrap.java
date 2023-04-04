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
    @JoinColumn(name = "postId")
    private Post post;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "petPostMissingId")
//    private PetPostMissing petPostMissing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petInfoByAPIdesertionNo", referencedColumnName = "desertionNo")
    private PetInfoByAPI petInfoByAPI;

    public void setPost(Post post) {
        this.post = post;
    }

//    public Scrap(Member member, PetPostMissing petPostMissing) {
//        this.member = member;
//        this.petPostMissing = petPostMissing;
//    }

    public Scrap(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public Scrap(Member member, PetInfoByAPI petInfoByAPI) {
        this.member = member;
        this.petInfoByAPI = petInfoByAPI;
    }
}