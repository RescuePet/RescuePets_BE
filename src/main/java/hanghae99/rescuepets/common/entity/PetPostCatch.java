package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PetPostCatch extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date postedDate;
    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;
    private boolean openNickname;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();


    public PetPostCatch(PetPostCatchRequestDto requestDto, Member member) {
        this.postedDate = requestDto.getPostedDate();
        this.happenPlace = requestDto.getHappenPlace();
        this.popfile = requestDto.getPopfile();
        this.kindCd = requestDto.getKindCd();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.member = member;
    }
    public void setOpenNickname(boolean isOpenNickname) {
        openNickname = isOpenNickname;
    }


    public void update(PetPostCatchRequestDto requestDto) {
        this.postedDate = requestDto.getPostedDate();
        this.happenPlace = requestDto.getHappenPlace();
        this.popfile = requestDto.getPopfile();
        this.kindCd = requestDto.getKindCd();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
    }
}
