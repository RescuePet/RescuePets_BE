package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.MemberPetRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PetPostMissing extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date postedDate;
    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "petPostMissing", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "petPostMissing", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();

    public PetPostMissing(PetPostMissingRequestDto requestDto, Member member) {
        this.postedDate = requestDto.getPostedDate();
        this.happenPlace = requestDto.getHappenPlace();
        this.popfile = requestDto.getPopfile();
        this.kindCd = requestDto.getKindCd();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.member = member;
    }

}

