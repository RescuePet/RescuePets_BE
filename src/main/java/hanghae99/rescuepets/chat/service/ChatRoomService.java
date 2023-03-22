package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    public ResponseEntity<ResponseDto> getRoomList(Member member) {
        List<ChatRoom> roomList = chatRoomRepository.findAllByHostIdOrGuestIdOrderByRoomIdDesc(member.getId(), member.getId());
        List<ChatRoomListResponseDto> dto = new ArrayList<>();
        for (ChatRoom room : roomList) {
            String lastChat = "";
            if (room.getChatMessages().size() > 0) {
                lastChat = room.getChatMessages().get(room.getChatMessages().size() - 1).getMessage();
            }
            String roomName = room.getHost().getNickname().equals(member.getNickname()) ? room.getGuest().getNickname() : room.getHost().getNickname();
            String profileImage = room.getHost().getNickname().equals(member.getNickname()) ? room.getGuest().getProfileImage() : room.getHost().getProfileImage();
            dto.add(ChatRoomListResponseDto.of(room, roomName, lastChat, profileImage));
        }

        return ResponseDto.toResponseEntity(SuccessMessage.Chat_Room_List_SUCCESS, dto);
    }

    public String createCatchRoom(Long postId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(postId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        ChatRoom room = chatRoomRepository.findChatRoomByCatchPostIdAndHostIdOrGuestId(post.getId(), post.getMember().getId(), member.getId()).orElse(
                ChatRoom.of(post, member));
        chatRoomRepository.save(room);

        return room.getRoomId();
    }

    public String createMissingRoom(Long postId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(postId).orElseThrow(() -> new CustomException(ExceptionMessage.POST_NOT_FOUND));
        ChatRoom room = chatRoomRepository.findChatRoomByMissingPostIdAndHostIdAndGuestId(post.getId(), post.getMember().getId(), member.getId()).orElse(
                ChatRoom.of(post, member));
        chatRoomRepository.save(room);

        return room.getRoomId();
    }
}
