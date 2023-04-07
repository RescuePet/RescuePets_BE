package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.common.util.Time;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.CREATE_CHAT_ROOM_EXCEPTION;
import static hanghae99.rescuepets.common.dto.ExceptionMessage.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<ResponseDto> getRoomList(Member member) {
        List<ChatRoom> roomList = chatRoomRepository.findAllByHostIdOrGuestIdOrderByModifiedAtDesc(member.getId(), member.getId());
        List<ChatRoomListResponseDto> dto = new ArrayList<>();
        for (ChatRoom room : roomList) {
            boolean isHost = room.isHost(member);
            if (isLeaved(isHost, room)) {
                continue;
            }
            ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder roomBuilder = ChatRoomListResponseDto.of(room, getPartner(isHost, room));
            if (!room.getChatMessages().isEmpty()) {
                Chat lastChat = room.getChatMessages().get(room.getChatMessages().size() - 1);
                roomBuilder.lastChat(lastChat.getMessage()).time(Time.chatTime(lastChat.getCreatedAt())).unreadChat(getUnreadChat(isHost, room));
            }
            dto.add(roomBuilder.build());
        }
        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_ROOM_LIST_SUCCESS, dto);
    }

    @Transactional
    public String createChatRoom(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (post.getMember().getId().equals(member.getId())) {
            throw new CustomException(CREATE_CHAT_ROOM_EXCEPTION);
        }
        ChatRoom room = chatRoomRepository.findChatRoomByPostIdAndGuestId(post.getId(), member.getId()).orElse(ChatRoom.of(post, member));
        chatRoomRepository.save(room);
        room.readChat(room.isHost(member));

        return room.getRoomId();
    }

    @Transactional
    public ResponseEntity<ResponseDto> exitRoom(String roomId, Member member) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.CHATROOM_NOT_FOUND));
        boolean isHost = room.isHost(member);
        exitRoom(isHost, room);
        return ResponseDto.toResponseEntity(SuccessMessage.CHAT_ROOM_EXIT_SUCCESS);
    }

    private Member getPartner(boolean isHost, ChatRoom room) {
        return isHost ? room.getGuest() : room.getHost();
    }

    private void exitRoom(boolean isHost, ChatRoom room) {
        if (isHost) {
            room.getHost().exitRoom(true);
        } else {
            room.getHost().exitRoom(true);
        }
        if (room.getHost().isExited() && room.getGuest().isExited()) {
            chatRoomRepository.deleteById(room.getId());
        }
    }

    private boolean isLeaved(boolean isHost, ChatRoom room) {
        return (isHost && room.getHost().isExited()) ||
                (!isHost && room.getGuest().isExited());
    }

    private int getUnreadChat(boolean isHost, ChatRoom room) {
        return isHost ? room.getGuest().getChatCount() : room.getHost().getChatCount();
    }
}


