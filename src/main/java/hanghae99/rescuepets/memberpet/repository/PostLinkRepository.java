package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLinkRepository extends JpaRepository<PostLink, Long> {
    List<PostLink> findAllByPost(Post post);
    List<PostLink> findAllByPostAndMemberId(Post post, Long memberId);
    void deleteByPostAndMemberId(Post post, Long memberId);
    void deleteByPostAndMemberIdAndLinkedPostId(Post post, Long memberId, Long linkedPostId);
    Optional<PostLink> findByPostId(Long postId);
    Optional<PostLink> findByPostAndMemberIdAndLinkedPostId(Post post, Long memberId, Long linkedPostId);
}
