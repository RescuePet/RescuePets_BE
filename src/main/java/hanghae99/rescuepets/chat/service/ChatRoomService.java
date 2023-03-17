package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    public List<ChatRoomListResponseDto> getRoomList(Member member) {
        List<ChatRoom> rooms = chatRoomRepository.findAllByHostIdOrGuestIdOrderByRoomIdDesc(member.getId(), member.getId());
        List<ChatRoomListResponseDto> dto = new ArrayList<>();
        for (ChatRoom room : rooms) {
            dto.add(ChatRoomListResponseDto.of(room));
        }

        return dto;
    }

    public String createCatchRoom(Long postId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(postId).orElseThrow(NullPointerException::new);
        ChatRoom room = chatRoomRepository.findChatRoomByCatchPostIdAndGuestId(postId, member.getId()).orElse(
                ChatRoom.of(post, member)
        );
        chatRoomRepository.save(room);
        return room.getRoomId();
    }

    public String createMissingRoom(Long postId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(postId).orElseThrow(NullPointerException::new);
        ChatRoom room = chatRoomRepository.findChatRoomByMissingPostIdAndGuestId(postId, member.getId()).orElse(
                ChatRoom.of(post, member)
        );
        chatRoomRepository.save(room);
        return room.getRoomId();
    }
}
