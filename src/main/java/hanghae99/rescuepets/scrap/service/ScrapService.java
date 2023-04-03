package hanghae99.rescuepets.scrap.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.publicpet.repository.PublicPetInfoRepository;
import hanghae99.rescuepets.scrap.dto.ScrapListResponseDto;
import hanghae99.rescuepets.scrap.dto.ScrapResponseDto;
import hanghae99.rescuepets.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;
    private final PublicPetInfoRepository publicPetInfoRepository;

    @Transactional
    public ResponseEntity<ResponseDto> scrapCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        Optional<Scrap> scrap = scrapRepository.findScrapByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (scrap.isPresent()) {
            throw new CustomException(ExceptionMessage.ALREADY_SCRAP);
        }
        scrapRepository.save(new Scrap(member, post));
        return ResponseDto.toResponseEntity(SuccessMessage.POST_SCRAP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> scrapMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Scrap> scrap = scrapRepository.findScrapByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (scrap.isPresent()) {
            throw new CustomException(ExceptionMessage.ALREADY_SCRAP);
        }
        scrapRepository.save(new Scrap(member, post));
        return ResponseDto.toResponseEntity(SuccessMessage.POST_SCRAP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> scrapPublicPet(String desertionNo, Member member) {
        PetInfoByAPI petInfoByAPI = publicPetInfoRepository.findByDesertionNo(desertionNo).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
        if (publicPetIsScrap(desertionNo, member.getId())) {
            throw new CustomException(DUPLICATE_RESOURCE_PET_INFO_SCRAP);
        }
        scrapRepository.save(new Scrap(member, petInfoByAPI));
        return ResponseDto.toResponseEntity(PET_INFO_SCRAP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteCatch(Long catchId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(catchId).orElseThrow(NullPointerException::new);
        Optional<Scrap> scrap = scrapRepository.findScrapByPetPostCatchIdAndMemberId(catchId, member.getId());
        if (scrap.isEmpty()) {
            throw new CustomException(ExceptionMessage.NOT_FOUND_SCRAP);
        }
        scrapRepository.deleteScrapByPetPostCatchIdAndMemberId(post.getId(), member.getId());
        return ResponseDto.toResponseEntity(SuccessMessage.DELETE_POST_SCRAP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteMissing(Long missingId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(missingId).orElseThrow(NullPointerException::new);
        Optional<Scrap> scrap = scrapRepository.findScrapByPetPostMissingIdAndMemberId(missingId, member.getId());
        if (scrap.isEmpty()) {
            throw new CustomException(ExceptionMessage.NOT_FOUND_SCRAP);
        }
        scrapRepository.deleteScrapByPetPostMissingIdAndMemberId(post.getId(), member.getId());
        return ResponseDto.toResponseEntity(SuccessMessage.DELETE_POST_SCRAP_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> deleteScrapPublicPet(String desertionNo, Member member) {
        if (!publicPetIsScrap(desertionNo, member.getId())) {
            throw new CustomException(NOT_FOUND_PET_INFO_SCRAP_MEMBER);
        }
        scrapRepository.deleteByMemberIdAndPetInfoByAPI_desertionNo(member.getId(), desertionNo);
        return ResponseDto.toResponseEntity(PET_INFO_SCRAP_DELETE_SUCCESS);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getMyScrapList(int page, int size, String sortBy, Member member) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Scrap> scrapPages = scrapRepository.findByMemberId(member.getId(), pageable);
        List<ScrapResponseDto> dtoList = new ArrayList<>();

        for (Scrap scrapPage : scrapPages) {
            if (scrapPage.getPetInfoByAPI() != null) {
                dtoList.add(ScrapResponseDto.of("publicPet", scrapPage.getId(), scrapPage.getPetInfoByAPI()));
            } else if (scrapPage.getPetPostCatch() != null) {
                dtoList.add(ScrapResponseDto.of("petCatch", scrapPage.getId(), scrapPage.getPetPostCatch()));
            } else if (scrapPage.getPetPostMissing() != null) {
                dtoList.add(ScrapResponseDto.of("petMissing", scrapPage.getId(), scrapPage.getPetPostMissing()));
            }
        }
        return ResponseDto.toResponseEntity(SCRAP_ALL_LIST_SUCCESS, ScrapListResponseDto.of(dtoList, scrapPages.isLast()));
    }

    private boolean publicPetIsScrap(String desertionNo, Long memberId) {
        return scrapRepository.findByMemberIdAndPetInfoByAPI_desertionNo(memberId, desertionNo).isPresent();
    }
}
