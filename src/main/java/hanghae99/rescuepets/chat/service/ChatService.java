package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatRoomResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRepository;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
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

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Member createChat(String roomId, ChatRequestDto dto) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(CHATROOM_NOT_FOUND));
        Member sender = memberRepository.findByNickname(dto.getSender()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));

        Chat message = Chat.of(dto, room, sender);
        chatRepository.save(message);

        setChatCount(sender, room);
        setExited(sender, room);

        return getPartner(sender, room);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getMessages(String roomId, Member member) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        readChat(member, room);

        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_HISTORY_SUCCESS, ChatRoomResponseDto.of(room));
    }

    private void setExited(Member sender, ChatRoom room) {
        if (room.isHost(sender)) {
            room.setGuestExited(false);
            return;
        }
        room.setHostExited(false);
    }

    private void setChatCount(Member sender, ChatRoom room) {
        if (room.isHost(sender)) {
            room.setHostChatCount(room.getHostChatCount() + 1);
            return;
        }
        room.setGuestChatCount(room.getGuestChatCount() + 1);
    }

    private void readChat(Member sender, ChatRoom room) {
        if (room.isHost(sender)) {
            room.initGuestChatCount();
            return;
        }
        room.initHostChatCount();
    }

    private Member getPartner(Member sender, ChatRoom room) {
        return room.isHost(sender) ? room.getGuest() : room.getHost();
    }
}