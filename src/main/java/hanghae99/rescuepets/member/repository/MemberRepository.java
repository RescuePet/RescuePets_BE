package hanghae99.rescuepets.member.repository;

import hanghae99.rescuepets.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);


    Optional<Member> findByNickname(String nickname);


    Optional<Member> findByKakaoId(Long kakaoId);

    int countByNickname(String nickname);
}
