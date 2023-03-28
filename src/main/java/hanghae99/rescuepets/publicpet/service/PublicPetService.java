package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetInfoLike;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.dto.PublicPetsResponsDto;
import hanghae99.rescuepets.publicpet.repository.PetInfoLikeRepository;
import hanghae99.rescuepets.publicpet.repository.PublicPetRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
    private final PetInfoLikeRepository petInfoLikeRepository;

    //전체 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getPublicPet(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "desertionNo").and(Sort.by(Sort.Direction.DESC, sortBy));
//        Sort sort = Sort.by(Sort.Direction.DESC, "desertionNo-" + sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetInfoByAPI> postPage = publicPetRepository.findAll(pageable);
        List<PublicPetResponsDto> dtoList = new ArrayList<>();

        for (PetInfoByAPI petInfoByAPI : postPage) {
            PublicPetResponsDto responseDto = PublicPetResponsDto.of(petInfoByAPI);
            dtoList.add(responseDto);
        }
        log.info("요청된 내용" + "page: " + page + "size: " + size + "sortBy: " + sortBy);
        log.info("dtoList contents: {}", "내용 물:    1번: " + dtoList.get(0).getDesertionNo() + "    2번:" + dtoList.get(1).getDesertionNo() + "    3번: " + dtoList.get(2).getDesertionNo() + "    4번: " + dtoList.get(3).getDesertionNo() + "    5번: " + dtoList.get(4).getDesertionNo());
        return ResponseDto.toResponseEntity(PET_INFO_GET_LIST_SUCCESS, PublicPetsResponsDto.of(dtoList, postPage.isLast()));
    }

    //상세 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> getPublicPetDetails(String desertionNo) {
        PetInfoByAPI petInfoByAPI = getPetInfo(desertionNo);
        return ResponseDto.toResponseEntity(PET_INFO_GET_DETAILS_SUCCESS, PublicPetResponsDto.of(petInfoByAPI));
    }

    //관심 유기동물 등록
    @Transactional
    public ResponseEntity<ResponseDto> petInfoLike(String desertionNo, Member member) {
        getPetInfo(desertionNo);
        if (petInfoLikeRepository.findByMemberIdAndDesertionNo(member.getId(), desertionNo).isPresent()) {
            throw new CustomException(DUPLICATE_RESOURCE_PET_INFO);
        }
        petInfoLikeRepository.save(new PetInfoLike(member, desertionNo));
        return ResponseDto.toResponseEntity(PET_INFO_WISH_SUCCESS);
    }

    //관심 유기동물 삭제
    @Transactional
    public ResponseEntity<ResponseDto> deletePetInfoLike(Long PetInfoLikeId, Member member) {
        if (petInfoLikeRepository.findByMemberIdAndId(member.getId(), PetInfoLikeId).isEmpty()) {
            throw new CustomException(NOT_FOUND_PET_INFO_MEMBER);
        }
        petInfoLikeRepository.deleteByMemberIdAndId(member.getId(), PetInfoLikeId);
        return ResponseDto.toResponseEntity(PET_INFO_WISH_DELETE_SUCCESS);
    }

    private PetInfoByAPI getPetInfo(String desertionNo) {
        return publicPetRepository.findByDesertionNo(desertionNo).orElseThrow(
                () -> new CustomException(NOT_FOUND_PET_INFO)
        );
    }
}
