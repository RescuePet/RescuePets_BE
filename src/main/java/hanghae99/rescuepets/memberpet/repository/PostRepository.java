package hanghae99.rescuepets.memberpet.repository;

import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMemberId(Long memberId);

    Page<Post> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    Page<Post> findByPostTypeOrderByCreatedAtDesc(PostTypeEnum postType, Pageable pageable);

    List<Post> findByOrderByCreatedAtDesc();

    Integer countByMemberId(Long memberId);

    List<Post> findALlByIsDeletedTrue();

    //위도 경도
    @Query(value = "SELECT * FROM post WHERE post.post_type = :postType AND ST_DISTANCE_SPHERE(POINT(:memberLatitude, :memberLongitude), POINT(post.happen_longitude, post.happen_latitude)) <= :distance ORDER BY post.created_at DESC", nativeQuery = true)
    Page<Post> findPostsByDistance(@Param("postType") String postType, @Param("memberLatitude") Double memberLatitude, @Param("memberLongitude") Double memberLongitude, @Param("distance") Double distance, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE post.post_type = :postType AND post.upkind LIKE :searchValue ORDER BY post.created_at DESC", nativeQuery = true)
    Page<Post> findPostsByUpkind(@Param("postType") String postType, @Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE post.post_type = :postType AND post.kind_cd LIKE :searchValue ", nativeQuery = true)
    Page<Post> findPostsByKindCd(@Param("postType") String postType, @Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE post.post_type = :postType AND post.upkind LIKE :searchValue AND ST_DISTANCE_SPHERE(POINT(:memberLatitude, :memberLongitude), POINT(post.happen_longitude, post.happen_latitude)) <= :distance ORDER BY post.created_at DESC", nativeQuery = true)
    Page<Post> findPostsByDistanceAndUpkind(@Param("postType") String postType, @Param("memberLatitude") Double userLatitude, @Param("memberLongitude") Double memberLongitude, @Param("distance") Double distance, @Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE post.post_type = :postType AND post.kind_cd LIKE :searchValue AND ST_DISTANCE_SPHERE(POINT(:memberLatitude, :memberLongitude), POINT(post.happen_longitude, post.happen_latitude)) <= :distance ORDER BY post.created_at DESC", nativeQuery = true)
    Page<Post> findPostsByDistanceAndKindCd(@Param("postType") String postType, @Param("memberLatitude") Double userLatitude, @Param("memberLongitude") Double memberLongitude, @Param("distance") Double distance, @Param("searchValue") String searchValue, Pageable pageable);




}
