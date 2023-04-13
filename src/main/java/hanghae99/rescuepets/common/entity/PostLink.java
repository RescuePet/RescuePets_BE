package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostLink extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", nullable = true)
    private Post post;
    @Column(nullable = false)
    private Long linkedPostId;
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    public PostLink(Post post, Long linkedPostId, Member member) {
        this.post = post;
        this.linkedPostId = linkedPostId;
        this.member = member;
    }

}
