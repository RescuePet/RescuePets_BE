package hanghae99.rescuepets.memberpet.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.PostImage;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingResponseDto;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.POST_NOT_FOUND;
import static hanghae99.rescuepets.common.dto.ExceptionMessage.UNAUTHORIZED_UPDATE_OR_DELETE;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class PetPostMissingService {
    private final PetPostMissingRepository petPostMissingRepository;
    private final WishRepository wishRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissingList(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostMissing> PetPostMissingPage = petPostMissingRepository.findAll(pageable);
        List<PetPostMissingResponseDto> dtoList = new ArrayList<>();

        for (PetPostMissing petPostMissing : PetPostMissingPage) {
            PetPostMissingResponseDto dto = PetPostMissingResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissingAll(String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        List<PetPostMissing> PetPostMissingList = petPostMissingRepository.findAll(sort);
        List<PetPostMissingResponseDto> dtoList = new ArrayList<>();
        for (PetPostMissing petPostMissing : PetPostMissingList) {
            PetPostMissingResponseDto dto = PetPostMissingResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissingListByMember(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostMissing> PetPostMissingPage = petPostMissingRepository.findByMemberId(member.getId(), pageable);
        List<PetPostMissingResponseDto> dtoList = new ArrayList<>();
        for (PetPostMissing petPostMissing : PetPostMissingPage) {
            PetPostMissingResponseDto dto = PetPostMissingResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(MY_POST_READING_SUCCESS, dtoList);
    }


    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissing(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        PetPostMissingResponseDto responseDto = PetPostMissingResponseDto.of(petPostMissing);
        responseDto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissingId, member.getId()).isPresent());

        return ResponseDto.toResponseEntity(POST_READING_SUCCESS, responseDto);
    }

    @Transactional
    public ResponseEntity<ResponseDto> create(PetPostMissingRequestDto requestDto, Member member) {
        List<String> postImageURLs = new ArrayList<>();
        if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
            postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
        }
        PetPostMissing petPostMissing = new PetPostMissing(requestDto, member);
        for (String postImageURL : postImageURLs) {
            petPostMissing.addPostImage(new PostImage(petPostMissing, postImageURL));
        }
        petPostMissingRepository.save(petPostMissing);
        return ResponseDto.toResponseEntity(POST_WRITING_SUCCESS);
    }


    @Transactional
    public ResponseEntity<ResponseDto> update(Long petPostMissingId, PetPostMissingRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            List<String> postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
            petPostMissing.getPostImages().clear();
            for (String postImageURL : postImageURLs) {
                petPostMissing.addPostImage(new PostImage(petPostMissing, postImageURL));
            }
            petPostMissing.update(requestDto);
            return ResponseDto.toResponseEntity(POST_MODIFYING_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> delete(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            petPostMissingRepository.deleteById(petPostMissingId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

}
