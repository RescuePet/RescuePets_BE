package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import hanghae99.rescuepets.chat.dto.ChatResponseDto;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate template;

    @Transactional
    public void createChat(String roomId, ChatRequestDto dto) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(CHATROOM_NOT_FOUND));
        Member sender = memberRepository.findByNickname(dto.getSender()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));
        boolean isHost = room.isHost(sender);

        Chat message = Chat.of(dto, room, sender);
        chatRepository.save(message);
        template.convertAndSend("/sub/" + roomId, ChatResponseDto.of(dto, sender));
        setChatCount(room, isHost);
        reEnterRoom(isHost, room);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getMessages(String roomId, Member member) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        member = getMember(member, room);
        room.readChat(member);
        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_HISTORY_SUCCESS, ChatRoomResponseDto.of(room));
    }

    private void reEnterRoom(boolean isHost, ChatRoom room) {
        if (isHost) {
            room.getHost().exitRoom(false);
        } else {
            room.getHost().exitRoom(false);
        }
    }

    private void setChatCount(ChatRoom room, boolean isHost) {
        if (isHost) {
            room.getHost().setChatCount();
        } else {
            room.getGuest().setChatCount();
        }
    }

    private Member getMember(Member member, ChatRoom room) {
        if (room.isHost(member)) {
            member = memberRepository.findById(room.getGuest().getId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        } else {
            member = memberRepository.findById(room.getHost().getId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        }
        return member;
    }
}