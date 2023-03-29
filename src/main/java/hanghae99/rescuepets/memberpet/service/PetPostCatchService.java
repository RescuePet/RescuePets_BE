package hanghae99.rescuepets.memberpet.service;


import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.memberpet.dto.*;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import hanghae99.rescuepets.memberpet.repository.PostLinkRepository;
import hanghae99.rescuepets.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;
import static hanghae99.rescuepets.common.entity.PostTypeEnum.CATCH;
import static hanghae99.rescuepets.common.entity.PostTypeEnum.MISSING;

@Service
@RequiredArgsConstructor
public class PetPostCatchService {
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;
    private final WishRepository wishRepository;
    private final PostLinkRepository postLinkRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<ResponseDto> createPetPostCatch(PetPostCatchRequestDto requestDto, Member member) {
        List<String> postImageURLs = new ArrayList<>();
        if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
            postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
        }
        PetPostCatch petPostCatch = new PetPostCatch(requestDto, member);
        for (String postImageURL : postImageURLs) {
            petPostCatch.addPostImage(new PostImage(petPostCatch, postImageURL));
        }
        petPostCatchRepository.save(petPostCatch);
        return ResponseDto.toResponseEntity(POST_WRITING_SUCCESS, petPostCatch.getId());
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPetPostCatchList(int page, int size, String sortBy, Member member) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostCatch> PetPostCatchPage = petPostCatchRepository.findAll(pageable);
        List<PetPostCatch> PetPostCatches = PetPostCatchPage.getContent();
        List<PetPostCatchShortResponseDto> dtoList = new ArrayList<>();
        for (PetPostCatch petPostCatch : PetPostCatches) {
            if(petPostCatch.getIsDeleted()){continue;}
            PetPostCatchShortResponseDto dto = PetPostCatchShortResponseDto.of(petPostCatch);
            dto.setWished(wishRepository.findWishByPetPostCatchIdAndMemberId(petPostCatch.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPetPostCatchAll(String sortBy, Member member) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        List<PetPostCatch> petPostCatchList = petPostCatchRepository.findAll(sort);
        List<PetPostCatchResponseDto> dtoList = new ArrayList<>();
        for (PetPostCatch petPostCatch : petPostCatchList) {
            if(petPostCatch.getIsDeleted()){continue;}
            PetPostCatchResponseDto dto = PetPostCatchResponseDto.of(petPostCatch);
            dto.setWished(wishRepository.findWishByPetPostCatchIdAndMemberId(petPostCatch.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPetPostCatchListByMember(int page, int size, String sortBy, Member member) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostCatch> PetPostCatchPage = petPostCatchRepository.findByMemberId(member.getId(), pageable);
        List<PetPostCatchShortResponseDto> dtoList = new ArrayList<>();
        for (PetPostCatch petPostCatch : PetPostCatchPage) {
            if(petPostCatch.getIsDeleted()){continue;}
            PetPostCatchShortResponseDto dto = PetPostCatchShortResponseDto.of(petPostCatch);
            dto.setWished(wishRepository.findWishByPetPostCatchIdAndMemberId(petPostCatch.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(MY_POST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostCatch(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(petPostCatch.getIsDeleted()){throw new CustomException(POST_ALREADY_DELETED);}
        PetPostCatchResponseDto responseDto = PetPostCatchResponseDto.of(petPostCatch);
        responseDto.setLinked(postLinkRepository.findByPetPostCatchId(petPostCatch.getId()).isPresent());
        responseDto.setWished(wishRepository.findWishByPetPostCatchIdAndMemberId(petPostCatchId, member.getId()).isPresent());
        return ResponseDto.toResponseEntity(POST_READING_SUCCESS, responseDto);
    }

    @Transactional
    public ResponseEntity<ResponseDto> updatePetPostCatch(Long petPostCatchId, PetPostCatchRequestDto requestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            List<String> postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
            petPostCatch.getPostImages().clear();
            for (String postImageURL : postImageURLs) {
                petPostCatch.addPostImage(new PostImage(petPostCatch, postImageURL));
            }
            petPostCatch.update(requestDto);
            return ResponseDto.toResponseEntity(POST_MODIFYING_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> softDeletePetPostCatch(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatch.setIsDeleted(true);
            return ResponseDto.toResponseEntity(POST_SOFT_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> deletePetPostCatch(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatchRepository.deleteById(petPostCatchId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> periodicDeletePetPostCatch(Long petPostCatchId, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(petPostCatch.getMember().getNickname())) {
            petPostCatchRepository.deleteById(petPostCatchId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> createLink(Long petPostCatchId, PostLinkRequestDto requestDto, Member member) {
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new NullPointerException("1단계에서 막힘ㅋ"));
        PostLink postLink = new PostLink(petPostCatch,requestDto,member);
        if((postLinkRepository.findByPetPostCatchAndMemberIdAndPostTypeAndLinkedPostId(petPostCatch,member.getId(),requestDto.getPostType(),requestDto.getLinkedPostId())).isPresent()){
            throw new NullPointerException("이미 존재하지롱");
        }
        postLinkRepository.save(postLink);
        PostLinkRequestDto requestDtoTemp = new PostLinkRequestDto(CATCH, petPostCatchId);
        if(requestDto.getPostType() == CATCH){
            if(petPostCatchId == requestDto.getLinkedPostId()){
                //사실 프론트 단에서 이런일은 미연에 방지할 것입니다. 넣을지 말지 고민 중
                throw new NullPointerException("자기 자신한테는 연결할 수 없지롱");
            }
            PetPostCatch petPostCatchTemp = petPostCatchRepository.findById(requestDto.getLinkedPostId()).orElseThrow(() -> new NullPointerException("3단계에서 막힘ㅋ"));
            postLinkRepository.save(new PostLink(petPostCatchTemp,requestDtoTemp,member));
        }else if(requestDto.getPostType() == MISSING){
            PetPostMissing petPostMissingTemp = petPostMissingRepository.findById(requestDto.getLinkedPostId()).orElseThrow(() -> new NullPointerException("4단계에서 막힘ㅋ"));
            postLinkRepository.save(new PostLink(petPostMissingTemp,requestDtoTemp,member));
        }
        return ResponseDto.toResponseEntity(POST_LINKING_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getLink(Long petPostCatchId, Member member){
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPetPostCatch(petPostCatch);
        List<PostLinkResponseDto> dtoList = new ArrayList<>();
        for (PostLink postLink : postLinkList) {
            PostLinkResponseDto responseDto = PostLinkResponseDto.of(postLink);
            if(member.getNickname().equals(postLink.getMember().getNickname())){
                responseDto.setLinkedByMe(true);
            }
            dtoList.add(responseDto);
        }
        return ResponseDto.toResponseEntity(POST_LINK_READING_SUCCESS, dtoList);
    }
    @Transactional
    public ResponseEntity<ResponseDto> deleteLink(Long petPostCatchId, Member member){
        PetPostCatch petPostCatch = petPostCatchRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPetPostCatchAndMemberId(petPostCatch, member.getId());
        for (PostLink postLink : postLinkList) {
            if(postLink.getPostType() == CATCH){
                PetPostCatch petPostCatchInverse = petPostCatchRepository.findById(postLink.getLinkedPostId()).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
                postLinkRepository.deleteByPetPostCatchAndMemberIdAndPostTypeAndLinkedPostId(petPostCatchInverse, member.getId(), CATCH, petPostCatchId);
            }else if(postLink.getPostType() == MISSING){
                PetPostMissing petPostMissingInverse = petPostMissingRepository.findById(postLink.getLinkedPostId()).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
//                왜 이렇게 복잡하게 조건을 걸어서 삭제하나요? 아래와 같이 삭제하면 해당 게시물에 걸려있는 모든 링크가 삭제되기 때문입니다.
//                현재 사용자가 삭제하려는 게시물쪽으로 걸린 링크 하나만 반대편에서 삭제해야하기 때문에 다음과 같이 삭제합니다.
                postLinkRepository.deleteByPetPostMissingAndMemberIdAndPostTypeAndLinkedPostId(petPostMissingInverse, member.getId(), CATCH, petPostCatchId);
            }
        }
        postLinkRepository.deleteByPetPostCatchAndMemberId(petPostCatch, member.getId());
        return ResponseDto.toResponseEntity(POST_LINK_DELETE_SUCCESS);
    }

}
