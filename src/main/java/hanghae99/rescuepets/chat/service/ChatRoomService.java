package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Chat;
import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.util.Time;
import hanghae99.rescuepets.memberpet.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

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
            if (isExited(member, room)) {
                continue;
            }
            ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder roomBuilder = ChatRoomListResponseDto.of(room, getPartner(member, room));
            if (!room.getChatMessages().isEmpty()) {
                Chat lastChat = room.getChatMessages().get(room.getChatMessages().size() - 1);
                roomBuilder.lastChat(lastChat.getMessage()).time(Time.chatTime(lastChat.getCreatedAt())).unreadChat(getUnreadChat(member, room));
            }
            dto.add(roomBuilder.build());
        }
        return ResponseDto.toResponseEntity(CHAT_ROOM_LIST_SUCCESS, dto);
    }

    @Transactional
    public String createChatRoom(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (post.getMember().getId().equals(member.getId())) {
            throw new CustomException(CREATE_CHAT_ROOM_EXCEPTION);
        }
        ChatRoom room = chatRoomRepository.findChatRoomByPostIdAndGuestId(post.getId(), member.getId()).orElse(ChatRoom.of(post, member));
        chatRoomRepository.save(room);
        readChat(member, room);

        return room.getRoomId();
    }

    @Transactional
    public ResponseEntity<ResponseDto> exitRoom(String roomId, Member member) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(ExceptionMessage.CHATROOM_NOT_FOUND));
        exitRoom(member, room);

        return ResponseDto.toResponseEntity(CHAT_ROOM_EXIT_SUCCESS);
    }

    private Member getPartner(Member member, ChatRoom room) {
        return room.isHost(member) ? room.getGuest() : room.getHost();
    }

    private void exitRoom(Member member, ChatRoom room) {
        if (room.isHost(member)) {
            room.setHostExited(true);
        } else {
            room.setGuestExited(true);
        }
        if (room.isHostExited() && room.isGuestExited()) {
            chatRoomRepository.deleteById(room.getId());
        }
    }

    private boolean isExited(Member member, ChatRoom room) {
        return (room.isHost(member) && room.isHostExited()) ||
                (!room.isHost(member) && room.isGuestExited());
    }

    private int getUnreadChat(Member member, ChatRoom room) {
        return room.isHost(member) ? room.getGuestChatCount() : room.getHostChatCount();
    }

    private void readChat(Member member, ChatRoom room) {
        if (room.isHost(member)) {
            room.initGuestChatCount();
            return;
        }
        room.initHostChatCount();
    }
}