package hanghae99.rescuepets.report.service;


import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.Report;
import hanghae99.rescuepets.mail.service.MailService;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import hanghae99.rescuepets.report.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.report.dto.ReportCommentRequestDto;
import hanghae99.rescuepets.report.dto.ReportPostRequestDto;
import hanghae99.rescuepets.report.dto.ReportResponseDto;
import hanghae99.rescuepets.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.ADMIN;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.MANAGER;
import static hanghae99.rescuepets.common.entity.ReportTypeEnum.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final MailService mailService;

    @Transactional
    public ResponseEntity<ResponseDto> reportMember(ReportMemberRequestDto reportMemberRequestDto, Member member) {
        if (reportMemberRequestDto.getNickname().equals(member.getNickname())){
            throw new CustomException(SELF_REFERENCE_NOT_ALLOWED);
        }
        Member respondent = memberRepository.findByNickname(reportMemberRequestDto.getNickname()).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );
        if (reportRepository.findByAccuserNicknameAndRespondentId(member.getNickname(), respondent.getId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .accuserNickname(member.getNickname())
                .reportTypeEnum(MEMBER_REPORT)
                .reportReasonEnum(reportMemberRequestDto.getReportReasonEnum())
                .content(reportMemberRequestDto.getContent())
                .respondent(respondent)
                .respondentNickname(respondent.getNickname())
                .build();

        int count = reportRepository.findByRespondentId(respondent.getId()).size() + 1;
        stopRespondent(count, respondent);
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportPost(ReportPostRequestDto reportPostRequestDto, Member member) {
        Post post = postRepository.findById(reportPostRequestDto.getPostId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        if (post.getMember().getNickname().equals(member.getNickname())){
            throw new CustomException(SELF_REFERENCE_NOT_ALLOWED);
        }
        // 중복 확인
        if (reportRepository.findByAccuserNicknameAndPostId(member.getNickname(), reportPostRequestDto.getPostId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .accuserNickname(member.getNickname())
                .reportTypeEnum(POST_REPORT)
                .reportReasonEnum(reportPostRequestDto.getReportReasonEnum())
                .content(reportPostRequestDto.getContent())
                .post(post)
                .respondentNickname(post.getMember().getNickname())
                .build();

        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
        int count = reportRepository.findByPostId(reportPostRequestDto.getPostId()).size()+1;
        Member respondent = memberRepository.findById(post.getMember().getId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        stopRespondent(count, respondent);
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportComment(ReportCommentRequestDto reportCommentRequestDto, Member member) {
        Comment comment = commentRepository.findById(reportCommentRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        if (comment.getMember().getNickname().equals(member.getNickname())){
            throw new CustomException(SELF_REFERENCE_NOT_ALLOWED);
        }
        if (reportRepository.findByAccuserNicknameAndCommentId(member.getNickname(), reportCommentRequestDto.getCommentId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .accuserNickname(member.getNickname())
                .reportTypeEnum(COMMENT_REPORT)
                .reportReasonEnum(reportCommentRequestDto.getReportReasonEnum())
                .content(reportCommentRequestDto.getContent())
                .comment(comment)
                .respondentNickname(comment.getMember().getNickname())
                .build();
        int count = reportRepository.findByCommentId(reportCommentRequestDto.getCommentId()).size()+1;
        Member informant = memberRepository.findById(comment.getMember().getId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        stopRespondent(count, informant);
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }

    public ResponseEntity<ResponseDto> getReportAll(String sortBy,Member member) {
        if(!(member.getMemberRoleEnum() == MANAGER || member.getMemberRoleEnum() == ADMIN)){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        List<Report> reports = reportRepository.findAll(sort);
        List<ReportResponseDto> dtoList = new ArrayList<>();
        for (Report report : reports) {
            ReportResponseDto dto = ReportResponseDto.of(report);
            dto.setRespondentInfo(memberRepository.findByNickname(report.getRespondentNickname()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND)));
            dto.setReportCount(reportRepository.countByRespondentNickname(report.getRespondentNickname()));
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(REPORT_LIST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportDelete(Long reportId, Member member) {
        Report report = validateReport(reportId);
        if(!(member.getMemberRoleEnum() == MANAGER || member.getMemberRoleEnum() == ADMIN)){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(REPORT_DELETE_SUCCESS);
    }

    private Report validateReport(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(
                () -> new CustomException(NOT_FOUND_REPORT)
        );
    }

    private void stopRespondent(int count, Member respondent) {
        if(count >15){
            respondent.stop(LocalDateTime.now());
            mailService.send(respondent);
        }
    }
}


