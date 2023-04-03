package hanghae99.rescuepets.member.service;

import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.member.dto.MyPageResponseDto;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.SuccessMessage.TEST_SUCCESS;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ScrapRepository scrapRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getMyPage(Member member) {
        Integer postCount = petPostCatchRepository.countByMemberId(member.getId()) + petPostMissingRepository.countByMemberId(member.getId());
        Integer commentCount = commentRepository.countByMemberId(member.getId());
        Integer scrapCount = scrapRepository.countByMemberId(member.getId());
        return ResponseDto.toResponseEntity(TEST_SUCCESS, MyPageResponseDto.of(postCount, commentCount, scrapCount));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getComment(Member member) {
        List<Comment> commentList= new ArrayList<>();
        return ResponseDto.toResponseEntity(TEST_SUCCESS);
    }


}
