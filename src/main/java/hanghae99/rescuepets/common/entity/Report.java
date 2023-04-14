package hanghae99.rescuepets.common.entity;


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
    private String accuserNickname;
    @Enumerated(value = EnumType.STRING)
    @Column
    private ReportTypeEnum reportTypeEnum;
    @Enumerated(value = EnumType.STRING)
    @Column
    private ReportReasonEnum reportReasonEnum;
    @Column(nullable = true)
    private String content;
    private String respondentNickname;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member respondent;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}

