package hanghae99.rescuepets.member.service;

import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.member.dto.MyPageResponseDto;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
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
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getMyPage(Member member) {
        Integer postCount = postRepository.countByMemberId(member.getId());
        Integer commentCount = commentRepository.countByMemberId(member.getId());
        Integer scrapCount = scrapRepository.countByMemberId(member.getId());
        return ResponseDto.toResponseEntity(TEST_SUCCESS, MyPageResponseDto.of(postCount, commentCount, scrapCount));
    }
}
