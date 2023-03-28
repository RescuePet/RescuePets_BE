package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostCatchId", nullable = true)
    private PetPostCatch petPostCatch;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissingId", nullable = true)
    private PetPostMissing petPostMissing;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostTypeEnum postType;
    @Column(nullable = false)
    private Long linkedPostId;
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    public PostLink(PetPostCatch petPostCatch, PostLinkRequestDto requestDto, Member member) {
        this.petPostCatch = petPostCatch;
        this.postType = requestDto.getPostType();
        this.linkedPostId = requestDto.getLinkedPostId();
        this.member = member;
    }

    public PostLink(PetPostMissing petPostMissing, PostLinkRequestDto requestDto, Member member) {
        this.petPostMissing = petPostMissing;
        this.postType = requestDto.getPostType();
        this.linkedPostId = requestDto.getLinkedPostId();
        this.member = member;
    }

}
