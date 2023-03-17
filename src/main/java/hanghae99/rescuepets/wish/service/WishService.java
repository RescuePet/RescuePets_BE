package hanghae99.rescuepets.wish.service;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.Wish;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    public String wishCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (wish.isPresent()) {
            wishRepository.deleteWishByPetPostCatchIdAndMemberId(catchId, member.getId());
            throw new IllegalArgumentException("이미 등록되었습니다.");
        }
        wishRepository.save(new Wish(member, post));
        return catchId + "번 게시글 관심 등록";
    }


    public String wishMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (wish.isPresent()) {
            wishRepository.deleteWishByPetPostMissingIdAndMemberId(missingId, member.getId());
            return missingId + "번 게시물 관심 취소";
        }
        wishRepository.save(new Wish(member, post));
        return missingId + "번 게시글 관심 등록";
    }

    public String deleteCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (wish.isEmpty()) {
            throw new IllegalArgumentException("관심 등록된 게시물이 아닙니다.");
        }
        wishRepository.deleteWishByPetPostCatchIdAndMemberId(catchId, member.getId());
        return catchId + "번 게시글 관심 해제";
    }

    public String deleteMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Wish> wish = wishRepository.findWishByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (wish.isEmpty()) {
            throw new IllegalArgumentException("관심 등록된 게시물이 아닙니다.");
        }
        wishRepository.deleteWishByPetPostMissingIdAndMemberId(missingId, member.getId());
        return missingId + "번 게시글 관심 해제";
    }
}
