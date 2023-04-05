package hanghae99.rescuepets.memberpet.service;


import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.memberpet.dto.*;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import hanghae99.rescuepets.memberpet.repository.PostLinkRepository;
import hanghae99.rescuepets.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;
import static hanghae99.rescuepets.common.entity.PostTypeEnum.MISSING;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ScrapRepository scrapRepository;
    private final PostLinkRepository postLinkRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<ResponseDto> createPost(PostRequestDto requestDto, Member member) {
        List<String> postImageURLs = new ArrayList<>();
        if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
            postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
        } else {
            throw new CustomException(NOT_FOUND_IMAGE);
        }
        Post post = new Post(requestDto, member);
        for (String postImageURL : postImageURLs) {
            post.addPostImage(new PostImage(post, postImageURL));
        }
        postRepository.save(post);
        return ResponseDto.toResponseEntity(POST_WRITING_SUCCESS, post.getId());
    }
    @Transactional
    public ResponseEntity<ResponseDto> setPostPoster(MissingPosterRequestDto requestDto, Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(post.getIsDeleted()){
            throw new CustomException(POST_ALREADY_DELETED);
        }
        if(post.getPostType() != MISSING){
            throw new CustomException(POST_TYPE_INCORRECT);
        }
        if(member.getNickname() == post.getMember().getNickname()){
            post.setMissingPosterImageURL(s3Uploader.uploadSingle(requestDto.getPostPoster()));
            return ResponseDto.toResponseEntity(POSTER_SAVING_SUCCESS);
        }else{
            throw new CustomException(UNAUTHORIZED_SAVE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPostList(int page, int size, String postType, Member member) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByPostTypeOrderByCreatedAtDesc(PostTypeEnum.valueOf(postType), pageable);
        List<PostShortResponseDto> dtoList = new ArrayList<>();
        for (Post post : postPage) {
            if(post.getIsDeleted()){continue;}
            PostShortResponseDto dto = PostShortResponseDto.of(post);
            dto.setWished(scrapRepository.findScrapByPostIdAndMemberId(post.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPostAll() {
        List<Post> postList = postRepository.findByOrderByCreatedAtDesc();
        List<PostResponseDto> dtoList = new ArrayList<>();
        for (Post post : postList) {
            if(post.getIsDeleted()){continue;}
            PostResponseDto dto = PostResponseDto.of(post);
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(POST_LIST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPostListByMember(int page, int size, Member member) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByMemberIdOrderByCreatedAtDesc(member.getId(), pageable);
        List<PostShortResponseDto> dtoList = new ArrayList<>();
        for (Post post : postPage) {
            if(post.getIsDeleted()){continue;}
            PostShortResponseDto dto = PostShortResponseDto.of(post);
            dto.setWished(scrapRepository.findScrapByPostIdAndMemberId(post.getId(), member.getId()).isPresent());
            dtoList.add(dto);
        }
        return ResponseDto.toResponseEntity(MY_POST_READING_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getPost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(post.getIsDeleted()){
            throw new CustomException(POST_ALREADY_DELETED);
        }
        PostResponseDto responseDto = PostResponseDto.of(post);
        responseDto.setLinked(postLinkRepository.findByPostId(post.getId()).isPresent());
        responseDto.setWished(scrapRepository.findScrapByPostIdAndMemberId(postId, member.getId()).isPresent());
        responseDto.setWishedCount(scrapRepository.countByPostId(postId));
        return ResponseDto.toResponseEntity(POST_DETAIL_READING_SUCCESS, responseDto);
    }
    @Transactional
    public ResponseEntity<ResponseDto> getPostPoster(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if(post.getIsDeleted()){
            throw new CustomException(POST_ALREADY_DELETED);
        }
        if(post.getPostType() == MISSING){
            if(post.getMissingPosterImageURL() == null){
                throw new CustomException(NOT_FOUND_IMAGE);
            }
            return ResponseDto.toResponseEntity(POSTER_READING_SUCCESS, post.getMissingPosterImageURL());
        }else{
            throw new CustomException(POST_TYPE_INCORRECT);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> updatePost(Long postId, PostRequestDto requestDto, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(post.getMember().getNickname())) {
            if (requestDto.getPostImages() != null && !requestDto.getPostImages().isEmpty()) {
                List<String> postImageURLs = s3Uploader.uploadMulti(requestDto.getPostImages());
                post.getPostImages().clear();
                for (String postImageURL : postImageURLs) {
                    post.addPostImage(new PostImage(post, postImageURL));
                }
            } else {
                throw new CustomException(NOT_FOUND_IMAGE);
            }
            post.update(requestDto);
            return ResponseDto.toResponseEntity(POST_MODIFYING_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> softDeletePetPostCatch(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(post.getMember().getNickname())) {
            post.setIsDeleted(true);
            return ResponseDto.toResponseEntity(POST_SOFT_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(post.getMember().getNickname())) {
            postRepository.deleteById(postId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }
    @Transactional
    public ResponseEntity<ResponseDto> periodicDeletePetPostCatch(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (member.getNickname().equals(post.getMember().getNickname())) {
            postRepository.deleteById(postId);
            return ResponseDto.toResponseEntity(POST_DELETE_SUCCESS);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> createLink(Long postId, PostLinkRequestDto requestDto, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("1단계에서 막힘ㅋ"));
        PostLink postLink = new PostLink(post,requestDto.getLinkedPostId(),member);
        if((postLinkRepository.findByPostAndMemberIdAndLinkedPostId(post,member.getId(),requestDto.getLinkedPostId())).isPresent()){
            throw new NullPointerException("이미 존재하지롱");
        }
        postLinkRepository.save(postLink);
        PostLinkRequestDto requestDtoTemp = new PostLinkRequestDto(postId);
        if(postId == requestDto.getLinkedPostId()){
            //사실 프론트 단에서 이런일은 미연에 방지할 것입니다. 넣을지 말지 고민 중
            throw new NullPointerException("자기 자신한테는 연결할 수 없지롱");
        }
        Post postTemp = postRepository.findById(requestDto.getLinkedPostId()).orElseThrow(() -> new NullPointerException("3단계에서 막힘ㅋ"));
        postLinkRepository.save(new PostLink(postTemp,requestDtoTemp.getLinkedPostId(),member));
        return ResponseDto.toResponseEntity(POST_LINKING_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getLink(Long petPostCatchId, Member member){
        Post post = postRepository.findById(petPostCatchId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPost(post);
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
    public ResponseEntity<ResponseDto> deleteLink(Long postId, Member member){
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<PostLink> postLinkList = postLinkRepository.findAllByPostAndMemberId(post, member.getId());
        for (PostLink postLink : postLinkList) {
            Post postInverse = postRepository.findById(postLink.getLinkedPostId()).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
            postLinkRepository.deleteByPostAndMemberIdAndLinkedPostId(postInverse, member.getId(), postId);
        }
        postLinkRepository.deleteByPostAndMemberId(post, member.getId());
        return ResponseDto.toResponseEntity(POST_LINK_DELETE_SUCCESS);
    }

}
