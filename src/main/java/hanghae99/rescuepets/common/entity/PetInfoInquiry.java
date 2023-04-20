package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PetInfoInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

    //연관관계 맺을지 고려
    @Column(nullable = false)
    private Long desertionNo;

    public PetInfoInquiry(Member member, Long desertionNo) {
        this.member = member;
        this.desertionNo = desertionNo;
    }
}
