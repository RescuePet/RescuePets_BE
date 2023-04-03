package hanghae99.rescuepets.report.service;


import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.report.dto.ReportIdRequestDto;
import hanghae99.rescuepets.report.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.report.dto.ReportRequestDto;
import hanghae99.rescuepets.report.dto.ResponseReportDto;
import hanghae99.rescuepets.report.repository.ReportRepository;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
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
    private final PetPostMissingRepository petPostMissingRepository;
    private final MemberRepository memberRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;


    @Transactional
    public ResponseEntity<ResponseDto> reportMissing(ReportRequestDto reportRequestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(reportRequestDto.getPetPostMissingId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        // 중복 확인
        if (reportRepository.findByMemberIdAndPetPostMissingId(member.getId(), reportRequestDto.getPetPostMissingId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }


        Report report = Report.builder()
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .petPostMissing(petPostMissing)
                .member(member)
                .build();
        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
        int count = reportRepository.findByPetPostMissing_Id(reportRequestDto.getPetPostMissingId()).size()+1;
        report.updates(count);
        if(count>15){
            report.getPetPostMissing().getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }



    public ResponseEntity<ResponseDto> reportMissingDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
       Report report = reportRepository.findByMemberIdAndPetPostMissingId(member.getId(), reportIdRequestDto.getPetPostMissingId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }


    @Transactional
    public ResponseEntity<ResponseDto> reportCatch(ReportRequestDto reportRequestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(reportRequestDto.getPetPostCatchId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        // 중복 확인
        if (reportRepository.findByMember_IdAndPetPostCatch_Id(member.getId(), reportRequestDto.getPetPostCatchId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        Report report = Report.builder()
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .petPostCatch(petPostCatch)
                .member(member)
                .build();

        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
        int count = reportRepository.findByPetPostCatchId(reportRequestDto.getPetPostCatchId()).size()+1;
        report.updates(count);
        if(count>15){
            report.getPetPostCatch().getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportCatchDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        Report report = reportRepository.findByMember_IdAndPetPostCatch_Id(member.getId(), reportIdRequestDto.getPetPostCatchId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportComment(ReportRequestDto reportRequestDto, Member member) {
        Comment comment = commentRepository.findById(reportRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        if (reportRepository.findByMember_IdAndComment_Id(member.getId(), reportRequestDto.getCommentId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        Report report = Report.builder()
                .comment(comment)
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .member(member)
                .build();
        int count = reportRepository.findByCommentId(reportRequestDto.getCommentId()).size()+1;
        report.updates(count);
        if(count>0){
            report.getComment().getMember().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);
        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }


    public ResponseEntity<ResponseDto> reportCommentDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        Report report = reportRepository.findByMember_IdAndComment_Id(member.getId(), reportIdRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> reportMember(ReportMemberRequestDto reportMemberRequestDto, Member respondent) {
        Member informant = memberRepository.findById(reportMemberRequestDto.getInformantId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_HUMAN)
        );
        if (reportRepository.findByInformant_IdAndRespondent_Id(reportMemberRequestDto.getInformantId(), respondent.getId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        Report report = Report.builder()
                .informant(informant)
                .respondent(respondent)
                .content(reportMemberRequestDto.getContent())
                .reportcode(reportMemberRequestDto.getReportCode().getValue())
                .build();

        int count = reportRepository.findByCommentId(reportMemberRequestDto.getInformantId()).size()+1;
        report.updates(count);
        if(count>0){
            report.getInformant().Stop(LocalDateTime.now());
        }
        reportRepository.save(report);


        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportMemberDelete(ReportMemberRequestDto reportMemberRequestDto, Member member) {
        Report report = reportRepository.findByInformant_IdAndRespondent_Id(reportMemberRequestDto.getInformantId(),member.getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> getReportAll(String sortBy) {
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


