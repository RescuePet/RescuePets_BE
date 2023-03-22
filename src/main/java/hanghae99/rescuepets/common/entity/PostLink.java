package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static hanghae99.rescuepets.common.entity.PostTypeEnum.*;

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

    public PostLink(PostLinkRequestDto requestDto, Member member) {
        this.member = member;
        this.petPostCatch = requestDto.getPetPostCatch();
        this.petPostMissing = requestDto.getPetPostMissing();
        this.postType = requestDto.getPostType();
        this.linkedPostId = requestDto.getLinkedPostId();
    }

}
