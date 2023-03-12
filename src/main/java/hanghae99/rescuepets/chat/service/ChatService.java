package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.repository.ChatRepository;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.entity.Chat;
import hanghae99.rescuepets.common.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void createChat(ChatRequestDto dto) {
        ChatRoom room = chatRoomRepository.findByRoomId(dto.getRoomId()).orElseThrow( () -> new NullPointerException("채팅방이 존재하지 않습니다."));

        Chat message = Chat.of(dto, room);
        chatRepository.save(message);
    }
}
