package hanghae99.rescuepets.comment.service;

import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
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
    private final PetPostMissingRepository petPostMissingRepository;
    private final PetPostCatchRepository petPostCatchRepository;

    @Transactional
    public ResponseEntity<ResponseDto> getCommentListByMember(Member member) {
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment, member));
        }
        return ResponseDto.toResponseEntity(MY_COMMENT_READING_SUCCESS, comments);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getCommentCatchList(PetPostCatch petPostCatch) {
        List<Comment> commentList = commentRepository.findAllByPetPostCatchId(petPostCatch.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment, petPostCatch));
        }
        return ResponseDto.toResponseEntity(COMMENT_READING_SUCCESS, comments);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getCommentMissingList(PetPostMissing petPostMissing) {
        List<Comment> commentList = commentRepository.findAllByPetPostMissingId(petPostMissing.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment, petPostMissing));
        }
        return ResponseDto.toResponseEntity(COMMENT_READING_SUCCESS, comments);
    }

    @Transactional
    public ResponseEntity<ResponseDto> createCommentCatch(Long petPostCatchId, CommentRequestDto requestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(()->new CustomException(POST_NOT_FOUND));
        commentRepository.save(new Comment(requestDto.getContent(), petPostCatch, member));
        return ResponseDto.toResponseEntity(COMMENT_WRITING_SUCCESS);
    }
    @Transactional
    public ResponseEntity<ResponseDto> createCommentMissing(Long petPostMissingId, CommentRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(()->new CustomException(POST_NOT_FOUND));
        commentRepository.save(new Comment(requestDto.getContent(), petPostMissing, member));
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
