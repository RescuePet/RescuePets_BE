package hanghae99.rescuepets.chat.repository;

import hanghae99.rescuepets.common.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String Id);

    List<ChatRoom> findAllByHostIdOrGuestIdOrderByRoomIdDesc(Long hostId, Long guestId);

    Optional<ChatRoom> findChatRoomByCatchPostIdAndGuestId(Long postId, Long guestId);

    Optional<ChatRoom> findChatRoomByMissingPostIdAndGuestId(Long postId, Long guestId);
}
