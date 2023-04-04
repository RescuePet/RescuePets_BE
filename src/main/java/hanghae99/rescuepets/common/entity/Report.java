package hanghae99.rescuepets.common.entity;


import hanghae99.rescuepets.report.dto.ReportRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report extends TimeStamped {
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
    @JoinColumn(name = "petpostcatch_id")
    private PetPostCatch petPostCatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petPostMissing_id")
    private PetPostMissing petPostMissing;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Member respondent;


    public void update(ReportRequestDto reportRequestDto) {
        this.content = reportRequestDto.getContent();
        this.reportcode =reportRequestDto.getReportCode().getValue();
    }

    public void updates(int count) {
        this.count = count;
    }

}

