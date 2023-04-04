package hanghae99.rescuepets.memberpet.repository;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByMemberId(Long memberId);
    Page<Post> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
    Page<Post> findByPostTypeOrderByCreatedAtDesc(PostTypeEnum postType, Pageable pageable);
    List<Post> findByOrderByCreatedAtDesc();
    Integer countByMemberId(Long memberId);

}
