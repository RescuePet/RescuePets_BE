package hanghae99.rescuepets.member.repository;

import hanghae99.rescuepets.common.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberEmail(String email);

    void deleteByMemberEmail(String email);
}
