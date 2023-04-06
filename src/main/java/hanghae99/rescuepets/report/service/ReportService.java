package hanghae99.rescuepets.report.service;


import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.report.dto.ReportIdRequestDto;
import hanghae99.rescuepets.report.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.report.dto.ReportRequestDto;
import hanghae99.rescuepets.report.dto.ResponseReportDto;
import hanghae99.rescuepets.report.repository.ReportRepository;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;




    @Transactional
    public ResponseEntity<ResponseDto> reportPost(ReportRequestDto reportRequestDto, Member member) {
        Post post = postRepository.findById(reportRequestDto.getPostId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        // 중복 확인
        if (reportRepository.findByMemberIdAndPostId(member.getId(), reportRequestDto.getPostId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .post(post)
                .member(member)
                .build();

        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
        int count = reportRepository.findByPostId(reportRequestDto.getPostId()).size()+1;
        report.updates(count);
        if(count>15){
            report.getPost().getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportPostDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        Report report = reportRepository.findByMemberIdAndPostId(reportIdRequestDto.getMemberId(), reportIdRequestDto.getPostId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_REPORT)
        );
        if(!(member.getMemberRoleEnum().getMemberRole().equals("매니저") || member.getMemberRoleEnum().getMemberRole().equals("관리자"))){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(REPORT_DELETE_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportComment(ReportRequestDto reportRequestDto, Member member) {
        Comment comment = commentRepository.findById(reportRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        if (reportRepository.findByMemberIdAndCommentId(member.getId(), reportRequestDto.getCommentId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .comment(comment)
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .member(member)
                .build();
        int count = reportRepository.findByCommentId(reportRequestDto.getCommentId()).size()+1;
        report.updates(count);
        if(count>15){
            report.getComment().getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);
        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }


    public ResponseEntity<ResponseDto> reportCommentDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        Report report = reportRepository.findByMemberIdAndCommentId(reportIdRequestDto.getMemberId(), reportIdRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_REPORT)
        );
        if(!(member.getMemberRoleEnum().getMemberRole().equals("매니저") || member.getMemberRoleEnum().getMemberRole().equals("관리자"))){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(REPORT_DELETE_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> reportMember(ReportMemberRequestDto reportMemberRequestDto, Member respondent) {
        Member member = memberRepository.findByNickname(reportMemberRequestDto.getNickname()).orElseThrow(
                () -> new CustomException(UNAUTHORIZED_MEMBER)
        );
        if (reportRepository.findByMemberIdAndRespondentId(reportMemberRequestDto.getInformantId(), respondent.getId()).isPresent()) {
            throw new CustomException(ALREADY_REPORT);
        }
        Report report = Report.builder()
                .member(member)
                .respondent(respondent)
                .content(reportMemberRequestDto.getContent())
                .reportcode(reportMemberRequestDto.getReportCode().getValue())
                .build();

        int count = reportRepository.findByCommentId(reportMemberRequestDto.getInformantId()).size()+1;
        report.updates(count);
        if(count>15){
            report.getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);


        return ResponseDto.toResponseEntity(REPORT_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportMemberDelete(ReportMemberRequestDto reportMemberRequestDto, Member member) {
        Report report = reportRepository.findByMemberNicknameAndMemberId(reportMemberRequestDto.getNickname(),reportMemberRequestDto.getInformantId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_REPORT)
        );
        if(!(member.getMemberRoleEnum().getMemberRole().equals("매니저") || member.getMemberRoleEnum().getMemberRole().equals("관리자"))){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(REPORT_DELETE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> getReportAll(String sortBy,Member member) {
        if(!(member.getMemberRoleEnum().getMemberRole().equals("매니저") || member.getMemberRoleEnum().getMemberRole().equals("관리자"))){
            throw new CustomException(UNAUTHORIZED_ADMIN);
        }
            Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
            List<Report> reports = reportRepository.findAll(sort);
            List<ResponseReportDto> dtoList = new ArrayList<>();
            for (Report report : reports) {
                ResponseReportDto dto = ResponseReportDto.of(report);
                dtoList.add(dto);
            }
            return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
        }
    }


