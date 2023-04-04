package hanghae99.rescuepets.memberpet.repository;
import hanghae99.rescuepets.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByMemberId(Long memberId);
    Page<Post> findByMemberId(Long memberId, Pageable pageable);
    Integer countByMemberId(Long memberId);

}
