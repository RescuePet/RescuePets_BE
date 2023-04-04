package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatRoomResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRepository;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.Chat;
import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createChat(String roomId, ChatRequestDto dto) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.CHATROOM_NOT_FOUND));
        Member sender = memberRepository.findByNickname(dto.getSender()).orElseThrow(() -> new CustomException(ExceptionMessage.UNAUTHORIZED_MEMBER));
        Chat message = Chat.of(dto, room, sender);

        chatRepository.save(message);
    }

    public ResponseEntity<ResponseDto> getMessages(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));

        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_HISTORY_SUCCESS, ChatRoomResponseDto.of(chatRoom));
    }
}