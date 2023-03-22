package hanghae99.rescuepets.declare.service;


import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.declare.dto.ReportIdRequestDto;
import hanghae99.rescuepets.declare.dto.ReportMemberRequestDto;
import hanghae99.rescuepets.declare.dto.ReportRequestDto;
import hanghae99.rescuepets.declare.repository.CommentReportRepository;
import hanghae99.rescuepets.declare.repository.MemberReportRepository;
import hanghae99.rescuepets.declare.repository.PostCatchReportRepository;
import hanghae99.rescuepets.declare.repository.PostMissingReportRepository;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PetPostMissingRepository petPostMissingRepository;
    private final MemberRepository memberRepository;
    private final PostMissingReportRepository postMissingReportRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PostCatchReportRepository postCatchReportRepository;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    private final MemberReportRepository memberReportRepository;

    @Transactional
    public ResponseEntity<ResponseDto> reportMissing(ReportRequestDto reportRequestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(reportRequestDto.getPetPostMissingId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        // 중복 확인
        if (postMissingReportRepository.findByMemberIdAndPetPostMissingId(member.getId(), reportRequestDto.getPetPostMissingId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        ReportMissing report = new ReportMissing(member, reportRequestDto, petPostMissing);

        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
//        int count = postMissingDeclareRepository.findByPetPostMissingId(declareRequestDto.getPetPostMissingId()).size();
//
//        declare.update(count);

        postMissingReportRepository.save(report);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportMissingPut(ReportRequestDto reportRequestDto, Member member) {
        ReportMissing report = postMissingReportRepository.findByMemberIdAndPetPostMissingId(member.getId(), reportRequestDto.getPetPostMissingId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        report.update(reportRequestDto);
        // 중복 확인
        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportMissingDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        ReportMissing report = postMissingReportRepository.findByMemberIdAndPetPostMissingId(member.getId(), reportIdRequestDto.getPetPostMissingId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        postMissingReportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }


    @Transactional
    public ResponseEntity<ResponseDto> reportCatch(ReportRequestDto reportRequestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(reportRequestDto.getPetPostCatchId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        // 중복 확인
        if (postCatchReportRepository.findByMember_IdAndPetPostCatch_Id(member.getId(), reportRequestDto.getPetPostCatchId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        ReportCatch reportCatch = ReportCatch.builder()
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .petPostCatch(petPostCatch)
                .member(member)
                .build();

        // 총개수 세기 필요 없을 수 도 있음 혹시 몰라서 써놓은 로직
//        int count = postMissingDeclareRepository.findByPetPostMissingId(declareRequestDto.getPetPostCatchId()).size();
//
//        declare.update(count);

        postCatchReportRepository.save(reportCatch);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportCatchPut(ReportRequestDto reportRequestDto, Member member) {
        ReportCatch report = postCatchReportRepository.findByMember_IdAndPetPostCatch_Id(member.getId(), reportRequestDto.getPetPostCatchId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        report.update(reportRequestDto);
        // 중복 확인
        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }

    public ResponseEntity<ResponseDto> reportCatchDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        ReportCatch report = postCatchReportRepository.findByMember_IdAndPetPostCatch_Id(member.getId(), reportIdRequestDto.getPetPostCatchId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        postMissingReportRepository.deleteById(report.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> reportComment(ReportRequestDto reportRequestDto, Member member) {
        Comment comment = commentRepository.findById(reportRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        if (commentReportRepository.findByMember_IdAndComment_Id(member.getId(), reportRequestDto.getCommentId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        ReportComment reportComment = ReportComment.builder()
                .comment(comment)
                .reportcode(reportRequestDto.getReportCode().getValue())
                .content(reportRequestDto.getContent())
                .member(member)
                .build();
        commentReportRepository.save(reportComment);
        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> reportCommentPut(ReportRequestDto reportRequestDto, Member member) {
        ReportComment reportComment = commentReportRepository.findByMember_IdAndComment_Id(member.getId(), reportRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportComment.update(reportRequestDto);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }


    public ResponseEntity<ResponseDto> reportCommentDelete(ReportIdRequestDto reportIdRequestDto, Member member) {
        ReportComment reportComment = commentReportRepository.findByMember_IdAndComment_Id(member.getId(), reportIdRequestDto.getCommentId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        commentReportRepository.deleteById(reportComment.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> reportMember(ReportMemberRequestDto reportMemberRequestDto, Member respondent) {
        Member informant = memberRepository.findById(reportMemberRequestDto.getInformantId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_HUMAN)
        );
        if (memberReportRepository.findByInformant_IdAndRespondent_Id(reportMemberRequestDto.getInformantId(), respondent.getId()).isPresent()) {
            throw new CustomException(ALREADY_DECLARE);
        }
        ReportMember reportMember = ReportMember.builder()
                .informant(informant)
                .respondent(respondent)
                .content(reportMemberRequestDto.getContent())
                .reportcode(reportMemberRequestDto.getReportCode().getValue())
                .build();
        memberReportRepository.save(reportMember);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> reporMemberPut(ReportMemberRequestDto reportMemberRequestDto, Member member) {
        ReportMember reportMember = memberReportRepository.findByInformant_IdAndRespondent_Id(reportMemberRequestDto.getInformantId(),member.getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        reportMember.report(reportMemberRequestDto);

        return ResponseDto.toResponseEntity(DECLARE_SUCCESS);
    }


    public ResponseEntity<ResponseDto> reportMemberDelete(ReportMemberRequestDto reportMemberRequestDto, Member member) {
        ReportMember reportMember = memberReportRepository.findByInformant_IdAndRespondent_Id(reportMemberRequestDto.getInformantId(),member.getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_DECLARE)
        );
        memberReportRepository.deleteById(reportMember.getId());

        return ResponseDto.toResponseEntity(DECLARE_DELETE_SUCCESS);
    }
}

