package hanghae99.rescuepets.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PetInfoScrap{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

    //마이페이지 구현하는거에 따라 변경되야함.
    @Column(nullable = false)
    private String desertionNo;

    public PetInfoScrap(Member member, String desertionNo) {
        this.member = member;
        this.desertionNo = desertionNo;
    }
}
