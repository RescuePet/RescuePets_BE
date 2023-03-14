//package hanghae99.rescuepets.memberpet.service;
//
//import hanghae99.rescuepets.common.dto.ResponseDto;
//import hanghae99.rescuepets.common.entity.Member;
//import hanghae99.rescuepets.common.entity.PetPostCatch;
//import hanghae99.rescuepets.common.entity.PetPostMissing;
//import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
//import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
//import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//public class PetPostMissingService {
//    private final PetPostMissingRepository petPostMissingRepository;
//    public ResponseDto<String> create(PetPostMissingRequestDto requestDto, MultipartFile multipartFile, Member member) throws IOException {
////        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
//        petPostMissingRepository.save(new PetPostMissing(requestDto, member));
//
//        return ResponseDto.success("게시물 등록 성공");
//    }
//}
