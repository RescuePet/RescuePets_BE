package hanghae99.rescuepets.comment.service;

import hanghae99.rescuepets.comment.dto.*;
import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.mail.service.MailService;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MailService mailService;

    @Transactional
    public ResponseEntity<ResponseDto> createComment(Long postId, CommentRequestDto requestDto, Member member) {
        if(!checkFrequency(member.getId())||!checkFrequencyDB()){
            throw new CustomException(TOO_FREQUENT_COMMENT);
        }
        Post post = postRepository.findById(postId).orElseThrow(()->new CustomException(POST_NOT_FOUND));
        commentRepository.save(new Comment(requestDto.getContent(), post, member));
        mailService.send(post, member.getNickname(), requestDto.getContent());
        return ResponseDto.toResponseEntity(COMMENT_WRITING_SUCCESS);
    }

    private Boolean checkFrequency(Long memberId) {
        List<Comment> commentList = commentRepository.findTop10ByMemberIdOrderByCreatedAtDesc(memberId);
        if (commentList.size() >= 10) {
            LocalDateTime fifthEntityCreatedAt = commentList.get(9).getCreatedAt();
            if(Duration.between(fifthEntityCreatedAt, LocalDateTime.now()).toMinutes()>=5){
                return true; // 최근순으로 정렬된 작성자의 댓글 중 10번 째 댓글이 5분이 지난 상태라면, "true"를 반환해 작성을 유도합니다.
            }else{
                return false; // 최근순으로 정렬된 작성자의 댓글 중 10번 째 댓글이 5분이 채 되지 않았다면, "false"를 반환합니다.
            }
        } else {
            return true; // 최근 작성된 작성자의 댓글의 총량이 10개가 되지 않는다면 바로 "true"를 반환해 작성을 유도합니다.
        }
    }

    private Boolean checkFrequencyDB() {
        List<Comment> commentList = commentRepository.findTop50ByOrderByCreatedAtDesc();
        if (commentList.size() == 50) {
            LocalDateTime fifthEntityCreatedAt = commentList.get(49).getCreatedAt();
            if(Duration.between(fifthEntityCreatedAt, LocalDateTime.now()).toMinutes()>=15){
                return true; // 최근순으로 정렬된 서버의 댓글 중 50번 째 댓글이 15분이 지났다면, "true"를 반환해 작성을 유도합니다.
            }else{
                return false; // 최근순으로 정렬된 서버의 댓글 중 50번 째 댓글이 15분이 채 되지 않았다면, "false"를 반환합니다.
            }
        } else {
            return true; // 최근 작성된 댓글의 총량이 50개가 되지 않는다면 바로 "true"를 반환해 작성을 유도합니다.
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> getCommentListByMember(int page, int size, Member member) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId(), pageable);
        List<CommentByMemberResponseDto> commentList = new ArrayList<>();
        for (Comment comment : commentPage) {
            if(comment!=null) {
                commentList.add(new CommentByMemberResponseDto(comment));
            }
        }
        return ResponseDto.toResponseEntity(MY_COMMENT_READING_SUCCESS, CommentByMemberResponseWithIsLastDto.of(commentList, commentPage.isLast()));
    }

    @Transactional
    public ResponseEntity<ResponseDto> getCommentList(int page, int size, Long postId) {
        Pageable pageable = PageRequest.of(page, size);
        Post post = postRepository.findById(postId).orElseThrow(()->new CustomException(POST_NOT_FOUND));
        Page<Comment> commentPage = commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId, pageable);
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : commentPage) {
            if(comment!=null) {
                commentList.add(new CommentResponseDto(comment));
            }
        }
        return ResponseDto.toResponseEntity(COMMENT_READING_SUCCESS, CommentResponseWithIsLastDto.of(commentList, commentPage.isLast()));
    }

    @Transactional
    public ResponseEntity<ResponseDto> update(Long commentId, CommentRequestDto requestDto, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(COMMENT_NOT_FOUND));
        if (comment.getMember().getNickname().equals(member.getNickname())) {
            comment.update(requestDto.getContent());
            return ResponseDto.toResponseEntity(COMMENT_MODIFYING_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> delete(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(COMMENT_NOT_FOUND));
        if (comment.getMember().getNickname().equals(member.getNickname())) {
            commentRepository.deleteById(commentId);
            return ResponseDto.toResponseEntity(COMMENT_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

}
