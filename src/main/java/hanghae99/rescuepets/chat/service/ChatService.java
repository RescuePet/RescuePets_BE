package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatResponseDto;
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
import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final SseService sseService;

    @Transactional
    public void createChat(String roomId, ChatRequestDto dto) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.CHATROOM_NOT_FOUND));
        Member sender = memberRepository.findByNickname(dto.getSender()).orElseThrow(() -> new CustomException(ExceptionMessage.UNAUTHORIZED_MEMBER));
        boolean isHost = room.isHost(sender);

        Chat message = Chat.of(dto, room, sender);
        chatRepository.save(message);
        setChatCount(isHost, room);
        reEnterRoom(isHost, room);
        sseService.send(getPartner(isHost, room), NotificationType.CHAT, sender.getNickname() + "님이 새로운 채팅을 보냈어요");
    }

    @Transactional
    public ResponseEntity<ResponseDto> getMessages(String roomId, Member member) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        readChat(room.isHost(member), room);
        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_HISTORY_SUCCESS, ChatRoomResponseDto.of(room));
    }

    private void reEnterRoom(boolean isHost, ChatRoom room) {
        if (isHost) {
            room.setGuestExited(false);
        } else {
            room.setHostExited(false);
        }
    }

    private void setChatCount(boolean isHost, ChatRoom room) {
        if (isHost) {
            room.setHostChatCount();
        } else {
            room.setGuestChatCount();
        }
    }

    private void readChat(boolean isHost, ChatRoom room) {
        if (isHost) {
            room.initGuestChatCount();
        } else {
            room.initHostChatCount();
        }
    }

    private Member getPartner(boolean isHost, ChatRoom room) {
        return isHost ? room.getGuest() : room.getHost();
    }
}