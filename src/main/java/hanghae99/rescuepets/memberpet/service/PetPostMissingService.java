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
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;
import static hanghae99.rescuepets.common.entity.PostTypeEnum.CATCH;
import static hanghae99.rescuepets.common.entity.PostTypeEnum.MISSING;

@Service
@RequiredArgsConstructor
public class PetPostMissingService {
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;
    private final WishRepository wishRepository;
    private final PostLinkRepository postLinkRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissingList(int page, int size, String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetPostMissing> PetPostMissingPage = petPostMissingRepository.findAll(pageable);
        List<PetPostMissingShortResponseDto> dtoList = new ArrayList<>();

        for (PetPostMissing petPostMissing : PetPostMissingPage) {
            if(petPostMissing.getIsDeleted()){continue;}
            PetPostMissingShortResponseDto dto = PetPostMissingShortResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissingAll(String sortBy, Member member) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        List<PetPostMissing> petPostMissingList = petPostMissingRepository.findAll(sort);
        List<PetPostMissingResponseDto> dtoList = new ArrayList<>();
        for (PetPostMissing petPostMissing : petPostMissingList) {
            if(petPostMissing.getIsDeleted()){continue;}
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
        List<PetPostMissingShortResponseDto> dtoList = new ArrayList<>();
        for (PetPostMissing petPostMissing : PetPostMissingPage) {
            if(petPostMissing.getIsDeleted()){continue;}
            PetPostMissingShortResponseDto dto = PetPostMissingShortResponseDto.of(petPostMissing);
            dto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissing.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(MY_POST_READING_SUCCESS, dtoList);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPetPostMissing(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(petPostMissing.getIsDeleted()){
            throw new CustomException(POST_ALREADY_DELETED);
        }
        PetPostMissingResponseDto responseDto = PetPostMissingResponseDto.of(petPostMissing);
        responseDto.setLinked(postLinkRepository.findByPetPostMissingId(petPostMissing.getId()).isPresent());
        responseDto.setWished(wishRepository.findWishByPetPostMissingIdAndMemberId(petPostMissingId, member.getId()).isPresent());
        responseDto.setWishedCount(wishRepository.countByPetPostMissingId(petPostMissingId));
        return ResponseDto.toResponseEntity(POST_READING_SUCCESS, responseDto);
    }

    @Transactional
    public ResponseEntity<ResponseDto> createPetPostMissing(PetPostMissingRequestDto requestDto, Member member) {
        List<String> postImageURLs = new ArrayList<>();
        if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
            postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
        }
        PetPostMissing petPostMissing = new PetPostMissing(requestDto, member);
        for (String postImageURL : postImageURLs) {
            petPostMissing.addPostImage(new PostImage(petPostMissing, postImageURL));
        }
        petPostMissingRepository.save(petPostMissing);
        return ResponseDto.toResponseEntity(POST_WRITING_SUCCESS, petPostMissing.getId());
    }


    @Transactional
    public ResponseEntity<ResponseDto> updatePetPostMissing(Long petPostMissingId, PetPostMissingRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(petPostMissing.getIsDeleted()){throw new CustomException(POST_NOT_FOUND);}
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            List<String> postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
            petPostMissing.getPostImages().clear();
            for (String postImageURL : postImageURLs) {
                petPostMissing.addPostImage(new PostImage(petPostMissing, postImageURL));
            }
            petPostMissing.update(requestDto);
            return ResponseDto.toResponseEntity(POST_MODIFYING_SUCCESS);
        }else{
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> softDelete(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(petPostMissing.getIsDeleted()){throw new CustomException(POST_NOT_FOUND);}
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())){
            petPostMissing.setIsDeleted(true);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> delete(Long petPostMissingId, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(petPostMissing.getIsDeleted()){throw new CustomException(POST_NOT_FOUND);}
        if (member.getNickname().equals(petPostMissing.getMember().getNickname())) {
            petPostMissingRepository.deleteById(petPostMissingId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> createLink(Long petPostMissingId, PostLinkRequestDto requestDto, Member member) {
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new NullPointerException("1단계에서 막힘ㅋ"));
        PostLink postLink = new PostLink(petPostMissing,requestDto,member);
        if((postLinkRepository.findByPetPostMissingAndMemberIdAndPostTypeAndLinkedPostId(petPostMissing,member.getId(),requestDto.getPostType(),requestDto.getLinkedPostId())).isPresent()){
            throw new NullPointerException("이미 존재하지롱");
        }
        postLinkRepository.save(postLink);
        PostLinkRequestDto requestDtoTemp = new PostLinkRequestDto(MISSING, petPostMissingId);
        if(requestDto.getPostType() == CATCH){
            PetPostCatch petPostCatchTemp = petPostCatchRepository.findById(requestDto.getLinkedPostId()).orElseThrow(() -> new NullPointerException("3단계에서 막힘ㅋ"));
            postLinkRepository.save(new PostLink(petPostCatchTemp,requestDtoTemp,member));
        }else if(requestDto.getPostType() == MISSING){
            if(petPostMissingId == requestDto.getLinkedPostId()){
                //사실 프론트 단에서 이런일은 미연에 방지할 것입니다. 넣을지 말지 고민 중
                throw new NullPointerException("자기 자신한테는 연결할 수 없지롱");
            }
            PetPostMissing petPostMissingTemp = petPostMissingRepository.findById(requestDto.getLinkedPostId()).orElseThrow(() -> new NullPointerException("4단계에서 막힘ㅋ"));
            postLinkRepository.save(new PostLink(petPostMissingTemp,requestDtoTemp,member));
        }
        return ResponseDto.toResponseEntity(POST_LINKING_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getLink(Long petPostMissingId, Member member){
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPetPostMissing(petPostMissing);
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
    public ResponseEntity<ResponseDto> deleteLink(Long petPostMissingId, Member member){
        PetPostMissing petPostMissing = petPostMissingRepository.findById(petPostMissingId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPetPostMissingAndMemberId(petPostMissing, member.getId());
        for (PostLink postLink : postLinkList) {
            if(postLink.getPostType() == CATCH){
                PetPostCatch petPostCatchInverse = petPostCatchRepository.findById(postLink.getLinkedPostId()).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
                postLinkRepository.deleteByPetPostCatchAndMemberIdAndPostTypeAndLinkedPostId(petPostCatchInverse, member.getId(), MISSING, petPostMissingId);
            }else if(postLink.getPostType() == MISSING){
                PetPostMissing petPostMissingInverse = petPostMissingRepository.findById(postLink.getLinkedPostId()).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
//                왜 이렇게 복잡하게 조건을 걸어서 삭제하나요? 리턴 위에서와 같이 삭제하면 해당 게시물에 걸려있는 모든 링크가 삭제되기 때문입니다.
//                현재 사용자가 삭제하려는 게시물쪽으로 걸린 링크 하나만 반대편에서 삭제해야하기 때문에 다음과 같이 삭제합니다.
                postLinkRepository.deleteByPetPostMissingAndMemberIdAndPostTypeAndLinkedPostId(petPostMissingInverse, member.getId(), MISSING, petPostMissingId);
            }
        }
        postLinkRepository.deleteByPetPostMissingAndMemberId(petPostMissing, member.getId());
        return ResponseDto.toResponseEntity(POST_LINK_DELETE_SUCCESS);
    }

}
