package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetInfoInquiry;
import hanghae99.rescuepets.common.entity.PetInfoScrap;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.dto.PublicPetsResponsDto;
import hanghae99.rescuepets.publicpet.repository.PetInfoInquiryRepository;
import hanghae99.rescuepets.publicpet.repository.PetInfoScrapRepository;
import hanghae99.rescuepets.publicpet.repository.PublicPetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicPetService {
    private final PublicPetRepository publicPetRepository;
    private final PetInfoScrapRepository petInfoScrapRepository;
    private final PetInfoInquiryRepository petInfoInquiryRepository;

    //전체 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getPublicPet(int page, int size, String sortBy, Member member) {
        Sort sort = Sort.by(Sort.Direction.DESC, "desertionNo", sortBy);
//        Sort sort = Sort.by(Sort.Direction.DESC, "desertionNo").and(Sort.by(Sort.Direction.DESC, sortBy));
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetInfoByAPI> postPage = publicPetRepository.findAll(pageable);
        List<PublicPetResponsDto> dtoList = new ArrayList<>();

        for (PetInfoByAPI petInfoByAPI : postPage) {
            Boolean isScrap = petInfoScrapRepository.findByMemberIdAndDesertionNo(member.getId(), petInfoByAPI.getDesertionNo()).isPresent();
            PublicPetResponsDto responseDto = PublicPetResponsDto.of(petInfoByAPI, isScrap);
            dtoList.add(responseDto);
        }
        return ResponseDto.toResponseEntity(PET_INFO_GET_LIST_SUCCESS, PublicPetsResponsDto.of(dtoList, postPage.isLast()));
    }

    //상세 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getPublicPetDetails(String desertionNo, Member member) {
        PetInfoByAPI petInfoByAPI = getPetInfo(desertionNo);
        Boolean isScrap = petInfoScrapRepository.findByMemberIdAndDesertionNo(member.getId(), desertionNo).isPresent();
        Integer scrapCount = petInfoScrapRepository.countByDesertionNo(desertionNo);
        Boolean isInquiry = petInfoInquiryRepository.findByMemberIdAndDesertionNo(member.getId(), desertionNo).isPresent();
        Integer InquiryCount = petInfoInquiryRepository.countByDesertionNo(desertionNo);
        return ResponseDto.toResponseEntity(PET_INFO_GET_DETAILS_SUCCESS, PublicPetResponsDto.of(petInfoByAPI, isScrap, scrapCount, isInquiry, InquiryCount));
    }

    //관심 유기동물 등록
    @Transactional
    public ResponseEntity<ResponseDto> petInfoScrap(String desertionNo, Member member) {
        getPetInfo(desertionNo);
        if (isScrap(desertionNo,member.getId())) {
            throw new CustomException(DUPLICATE_RESOURCE_PET_INFO_SCRAP);
        }
        petInfoScrapRepository.save(new PetInfoScrap(member, desertionNo));
        return ResponseDto.toResponseEntity(PET_INFO_SCRAP_SUCCESS);
    }

    //관심 유기동물 삭제
    @Transactional
    public ResponseEntity<ResponseDto> deletePetInfoScrap(String desertionNo, Member member) {
        if (!isScrap(desertionNo,member.getId())) {
            throw new CustomException(NOT_FOUND_PET_INFO_SCRAP_MEMBER);
        }
        petInfoScrapRepository.deleteByMemberIdAndDesertionNo(member.getId(), desertionNo);
        return ResponseDto.toResponseEntity(PET_INFO_SCRAP_DELETE_SUCCESS);
    }

    //문의 기록
    @Transactional
    public ResponseEntity<ResponseDto> petInfoInquiry(String desertionNo, Member member) {
        getPetInfo(desertionNo);
        if (petInfoInquiryRepository.findByMemberIdAndDesertionNo(member.getId(), desertionNo).isPresent()) {
            throw new CustomException(DUPLICATE_RESOURCE_PET_INFO_INQUIRY);
        }
        petInfoInquiryRepository.save(new PetInfoInquiry(member, desertionNo));
        return ResponseDto.toResponseEntity(PET_INFO_INQUIRY_SUCCESS);
    }

    private PetInfoByAPI getPetInfo(String desertionNo) {
        return publicPetRepository.findByDesertionNo(desertionNo).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
    }
    private boolean isScrap(String desertionNo, Long memberId){
        return petInfoScrapRepository.findByMemberIdAndDesertionNo(memberId, desertionNo).isPresent();
    }
}
