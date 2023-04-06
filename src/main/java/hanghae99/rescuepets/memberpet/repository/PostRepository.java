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

//    @Query("SELECT p FROM post p WHERE ST_DISTANCE_SPHERE(POINT(:userLatitude, :userLongitude), POINT(p.happenLatitude, p.happenLongitude)) <= 100000")
//    List<Post> findPostsByDistance(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude, @Param("happenLongitude") Double happenLongitude, @Param("happenLatitude") Double happenLatitude);

//    @Query("SELECT p FROM post p WHERE ST_DISTANCE_SPHERE(POINT(:userLatitude, :userLongitude), POINT(p.happenLatitude, p.happenLongitude)) <= :distance")
//    List<Post> findPostsByDistance(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude, @Param("distance") Double distance);

    @Query("SELECT p FROM post p WHERE ST_DISTANCE_SPHERE(POINT(:userLatitude, :userLongitude), POINT(p.happenLongitude, p.happenLatitude)) <= :distance")
    List<Post> findPostsByDistance(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude, @Param("distance") Double distance);

//    @Query("SELECT p FROM post p WHERE ST_DISTANCE_SPHERE(POINT(:userLatitude, :userLongitude), POINT(p.happenLongitude, p.happenLatitude)) <= 100000")
//    List<Post> findPostsByDistance(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude);


}
