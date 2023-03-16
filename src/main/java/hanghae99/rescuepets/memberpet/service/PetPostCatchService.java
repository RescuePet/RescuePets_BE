package hanghae99.rescuepets.memberpet.service;


import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PostImage;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchResponseDto;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetPostCatchService {
    private final PetPostCatchRepository petPostCatchRepository;
    private final WishRepository wishRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<List<PetPostCatchResponseDto>> getPetPostCatchList(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostCatch> PetPostCatchPage = petPostCatchRepository.findAll(pageable);

        List<PetPostCatch> PetPostCatches = PetPostCatchPage.getContent();
        List<PetPostCatchResponseDto> dtoList = new ArrayList<>();

        for (PetPostCatch petPostCatch : PetPostCatches) {
            PetPostCatchResponseDto dto = PetPostCatchResponseDto.of(petPostCatch);
            dto.setWished(wishRepository.findByPetPostCatchIdAndMemberId(petPostCatch.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<PetPostCatchResponseDto> getPetPostCatch(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                new CustomException(ErrorCode.NotFoundPost)
        );
        PetPostCatchResponseDto responseDto = PetPostCatchResponseDto.of(petPostCatch);
        responseDto.setWished(wishRepository.findByPetPostCatchIdAndMemberId(petPostCatchId, member.getId()).isPresent());
        return ResponseDto.success(responseDto);
    }

    @Transactional
    public ResponseDto<String> create(PetPostCatchRequestDto requestDto, Member member) {
        List<String> postImageURLs = new ArrayList<>();
        if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
            postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
        }
        PetPostCatch petPostCatch = new PetPostCatch(requestDto, member);
        for (String postImageURL : postImageURLs) {
            petPostCatch.addPostImage(PostImage.builder().petPostCatch(petPostCatch).imageURL(postImageURL).build());
        }
        petPostCatchRepository.save(petPostCatch);
        return ResponseDto.success("게시물 등록 성공");
    }


    @Transactional
    public ResponseDto<String> update(Long petPostCatchId, PetPostCatchRequestDto requestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        String imageUrl = s3Uploader.uploadSingle(requestDto.getPopfile());
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatch.update(requestDto, imageUrl);
            return ResponseDto.success("게시물 수정 성공");
        } else {
            throw new NullPointerException("수정권한이 없는데용")
//                    CustomException(ErrorCode.NoModifyPermission)
                    ;
        }
    }

    @Transactional
    public ResponseDto<String> delete(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatchRepository.deleteById(petPostCatchId);
            return ResponseDto.success("게시물 삭제 성공");
        } else {
            throw new NullPointerException("삭제권한이 없는데용")
//                    CustomException(ErrorCode.NoDeletePermission)
                    ;
        }
    }

}
