package hanghae99.rescuepets.memberpet.service;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingResponseDto;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.memberpet.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetPostMissingService {
    private final PetPostMissingRepository petPostMissingRepository;
    private final WishRepository wishRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<List<PetPostMissingResponseDto>> getPetPostMissingList(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostMissing> PetPostMissingPage = petPostMissingRepository.findAll(pageable);

        List<PetPostMissing> PetPostMissingList = PetPostMissingPage.getContent();
        List<PetPostMissingResponseDto> dtoList = new ArrayList<>();

        for (PetPostMissing petPostMissing : PetPostMissingList) {
            PetPostMissingResponseDto dto = PetPostMissingResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<PetPostMissingResponseDto> getPetPostMissing(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                new CustomException(ErrorCode.NotFoundPost)
        );
        PetPostMissingResponseDto responseDto = PetPostMissingResponseDto.of(petPostMissing);
        responseDto.setWished(wishRepository.findByPetPostMissingIdAndMemberId(petPostMissingId, member.getId()).isPresent());
        return ResponseDto.success(responseDto);
    }

    @Transactional
    public ResponseDto<String> createPetPostMissing(PetPostMissingRequestDto requestDto, Member member) {
        String imageUrl = s3Uploader.uploadSingle(requestDto.getPopfile());
        petPostMissingRepository.save(new PetPostMissing(requestDto, imageUrl, member));

        return ResponseDto.success("게시물 등록 성공");
    }


    @Transactional
    public ResponseDto<String> updatePetPostMissing(Long petPostMissingId, PetPostMissingRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        String imageUrl = s3Uploader.uploadSingle(requestDto.getPopfile());
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            petPostMissing.update(requestDto, imageUrl);
            return ResponseDto.success("게시물 수정 성공");
        } else {
            throw new NullPointerException("수정권한이 없는데용")
//                    CustomException(ErrorCode.NoModifyPermission)
                    ;
        }
    }

    @Transactional
    public ResponseDto<String> deletePetPostMissing(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            petPostMissingRepository.deleteById(petPostMissingId);
            return ResponseDto.success("게시물 삭제 성공");
        } else {
            throw new NullPointerException("삭제권한이 없는데용")
//                    CustomException(ErrorCode.NoDeletePermission)
                    ;
        }
    }

}
