package hanghae99.rescuepets.chat.repository;

import hanghae99.rescuepets.common.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
    List<ChatMember> findAllByMember(Member member);

    Optional<ChatMember> findByCatchPostAndMember(PetPostCatch post, Member member);

    Optional<ChatMember> findByMissingPostAndMember(PetPostMissing post, Member member);

    Optional<ChatMember> findByRoomAndMember(ChatRoom room, Member member);

    List<ChatMember> findAllByRoomAndCatchPost(ChatRoom room, PetPostCatch postCatch);
}
