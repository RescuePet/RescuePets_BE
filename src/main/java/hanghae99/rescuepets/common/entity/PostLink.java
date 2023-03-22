package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PostLinkCTCRequestDto;
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
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostTypeEnum postType;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Long linkedPostId;
}
