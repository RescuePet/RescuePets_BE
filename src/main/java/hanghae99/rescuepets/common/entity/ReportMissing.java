package hanghae99.rescuepets.common.entity;


import hanghae99.rescuepets.declare.dto.ReportRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReportMissing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int count;

    @Column(nullable = true)
    private String content;

    @Column
    private String reportcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissing_id")
    private PetPostMissing petPostMissing;




    public ReportMissing(Member member, ReportRequestDto declareRequestDto, PetPostMissing petPostMissing) {
        this.member = member;
        this.petPostMissing = petPostMissing;
        this.content = declareRequestDto.getContent();
        this.reportcode = declareRequestDto.getReportCode().getValue();
    }

    public void update(ReportRequestDto reportRequestDto) {
         this.reportcode = reportRequestDto.getReportCode().getValue();
         this.content =reportRequestDto.getContent();
    }
}

