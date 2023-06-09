package hanghae99.rescuepets.member.repository;

import hanghae99.rescuepets.common.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);
    Page<Member> findByOrderByCreatedAtDesc(Pageable pageable);
    Optional<Member> findByNickname(String nickname);
    Page<Member> findByNicknameContaining(String keyword, Pageable pageable);
    int countByNickname(String nickname);
}
