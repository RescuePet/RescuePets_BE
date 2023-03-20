package hanghae99.rescuepets.wish.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.Wish;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    @Transactional
    public ResponseEntity<ResponseDto> wishCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        Optional<Wish> wish = wishRepository.findWishByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (wish.isPresent()) {
            throw new CustomException(ExceptionMessage.ALREADY_WISH);
        }
        wishRepository.save(new Wish(member, post));
        return ResponseDto.toResponseEntity(SuccessMessage.POST_WISH_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> wishMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (wish.isPresent()) {
            throw new CustomException(ExceptionMessage.ALREADY_WISH);
        }
        wishRepository.save(new Wish(member, post));
        return ResponseDto.toResponseEntity(SuccessMessage.POST_WISH_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (wish.isEmpty()) {
            throw new CustomException(ExceptionMessage.NOT_FOUND_WISH);
        }
        wishRepository.deleteWishByPetPostCatchIdAndMemberId(post.getId(), member.getId());
        return ResponseDto.toResponseEntity(SuccessMessage.DELETE_POST_WISH_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (wish.isEmpty()) {
            throw new CustomException(ExceptionMessage.NOT_FOUND_WISH);
        }
        wishRepository.deleteWishByPetPostMissingIdAndMemberId(post.getId(), member.getId());
        return ResponseDto.toResponseEntity(SuccessMessage.DELETE_POST_WISH_SUCCESS);
    }
}
