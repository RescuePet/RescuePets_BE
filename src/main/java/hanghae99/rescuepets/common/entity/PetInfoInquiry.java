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

    @Column(nullable = false)
    private String desertionNo;

    public PetInfoInquiry(Member member, String desertionNo) {
        this.member = member;
        this.desertionNo = desertionNo;
    }
}
