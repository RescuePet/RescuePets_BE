package hanghae99.rescuepets.comment.service;

import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.mail.service.MailService;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import hanghae99.rescuepets.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public ResponseEntity<ResponseDto> getCommentListByMember(Member member) {
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment));
        }
        return ResponseDto.toResponseEntity(MY_COMMENT_READING_SUCCESS, comments);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getCommentList(Post post) {
        List<Comment> commentList = commentRepository.findAllByPostId(post.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment, post));
        }
        return ResponseDto.toResponseEntity(COMMENT_READING_SUCCESS, comments);
    }

    @Transactional
    public ResponseEntity<ResponseDto> createComment(Long postId, CommentRequestDto requestDto, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(()->new CustomException(POST_NOT_FOUND));
        commentRepository.save(new Comment(requestDto.getContent(), post, member));
        mailService.send(post.getMember(), member.getNickname());
        return ResponseDto.toResponseEntity(COMMENT_WRITING_SUCCESS);
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
