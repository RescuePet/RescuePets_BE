package hanghae99.rescuepets.comment.service;

import hanghae99.rescuepets.comment.repository.CommentRepository;
import hanghae99.rescuepets.comment.dto.CommentRequestDto;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Comment;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    @Transactional
    public ResponseDto<List<CommentResponseDto>> getCommentList(Member member) {
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment, member));
        }
        return ResponseDto.success(comments);
    }
    @Transactional
    public ResponseDto<List<CommentResponseDto>> getCommentCurrentList(Long petPostMissingId) {
        List<Comment> commentList = commentRepository.findAllByPetPostMissingId(petPostMissingId);
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(new CommentResponseDto(comment));
        }
        return ResponseDto.success(comments);
    }
    @Transactional
    public ResponseDto<String> create(Long petPostMissingId, CommentRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("댓글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        commentRepository.save(new Comment(requestDto.getContent(), petPostMissing, member));
        return ResponseDto.success("댓글 등록 성공");
    }

//    @Transactional
//    public ResponseDto<String> update(Long petPostMissingId, CommentRequestDto requestDto, Member member) {
//        Comment comment = commentRepository.findById(requestDto.getId()).orElseThrow(() -> new NullPointerException("댓글이 없는데용")
////                CustomException(ErrorCode.NotFoundComment)
//        );
//        if (comment.getMember().getNickname().equals(member.getNickname())) {
//            comment.update(requestDto.getContent());
//            return ResponseDto.success("댓글 수정 성공");
//        } else {
//            throw new NullPointerException("수정권한이 없는데용");
////                    CustomException(ErrorCode.NoModifyPermission);
//        }
//    }
//
//    @Transactional
//    public ResponseDto<String> delete(Long petPostMissingId, Member member) {
//        Comment comment = commentRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("댓글이 없는데용")
////                CustomException(ErrorCode.NotFoundComment)
//        );
//        if (comment.getMember().getNickname().equals(member.getNickname())) {
//            commentRepository.deleteById(petPostMissingId);
//            return ResponseDto.success("댓글 삭제 성공");
//        } else {
//            throw new NullPointerException("삭제권한이 없는데용");
////                    CustomException(ErrorCode.NoDeletePermission);
//        }
//    }

}
