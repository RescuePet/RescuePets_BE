package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.declare.dto.ReportMemberRequestDto;
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
public class ReportMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String content;

    @Column
    private String reportcode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member informant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member respondent;

    public void report(ReportMemberRequestDto reportMemberRequestDto) {
        this.content =reportMemberRequestDto.getContent();
        this.reportcode = reportMemberRequestDto.getReportCode().getValue();
    }
}
