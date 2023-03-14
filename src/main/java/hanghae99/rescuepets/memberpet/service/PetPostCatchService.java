package hanghae99.rescuepets.memberpet.service;


import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
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

    @Transactional
    public ResponseDto<List<PetPostCatchResponseDto>> getPetPostCatchList(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostCatch> PetPostCatchPage = petPostCatchRepository.findAll(pageable);

        List<PetPostCatch> PetPostCatchs = PetPostCatchPage.getContent();
        List<PetPostCatchResponseDto> dtoList = new ArrayList<>();

        for (PetPostCatch petPostCatch : PetPostCatchs) {
            boolean isOpenNickname = false;
            PetPostCatchResponseDto responseDto = PetPostCatchResponseDto.of(petPostCatch);
            if(member != null) { // 로그인 했을 때 좋아요 여부 체크
                isOpenNickname = wishRepository.findByPostIdAndUserId(petPostCatch.getId(),member.getId()).isPresent();
            }
            responseDto.setOpenNickname(isOpenNickname);
            dtoList.add(responseDto);
        }
        return ResponseDto.success(dtoList);
    }
    @Transactional
    public ResponseDto<String> create(PetPostCatchRequestDto requestDto, MultipartFile multipartFile, Member member) throws IOException {
//        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        petPostCatchRepository.save(new PetPostCatch(requestDto, member));

        return ResponseDto.success("게시물 등록 성공");
    }


    @Transactional
    public ResponseDto<String> update(Long petPostCatchId, PetPostCatchRequestDto requestDto, MultipartFile multipartFile, Member member) throws IOException {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
//        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatch.update(requestDto);
            return ResponseDto.success("게시물 수정 성공");
        } else {
            throw new NullPointerException("수정권한이 없는데용")
//                    CustomException(ErrorCode.NoModifyPermission)
                    ;
        }
    }

    @Transactional
    public ResponseDto<String> delete(Long postId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없는데용")
//                CustomException(ErrorCode.NotFoundPost)
        );
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatchRepository.deleteById(postId);
            return ResponseDto.success("게시물 삭제 성공");
        } else {
            throw new NullPointerException("삭제권한이 없는데용")
//                    CustomException(ErrorCode.NoDeletePermission)
                    ;
        }
    }

}
