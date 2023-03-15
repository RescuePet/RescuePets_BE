package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PetPostCatch extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;
    private Boolean openNickname;



    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();


    public PetPostCatch(PetPostCatchRequestDto requestDto, String imageUrl, Member member) {
        this.happenPlace = requestDto.getHappenPlace();
        this.popfile = imageUrl;
        this.kindCd = requestDto.getKindCd();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.member = member;
        this.openNickname = requestDto.getOpenNickname();
    }

    public void update(PetPostCatchRequestDto requestDto, String imageUrl) {
        this.happenPlace = requestDto.getHappenPlace();
        this.popfile = imageUrl;
        this.kindCd = requestDto.getKindCd();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.openNickname = requestDto.getOpenNickname();
    }
}
