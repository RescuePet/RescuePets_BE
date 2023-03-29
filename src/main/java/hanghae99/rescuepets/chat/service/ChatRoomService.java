package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.ChatRoom;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostMissingRepository;
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
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    @Transactional
    public ResponseEntity<ResponseDto> getRoomList(Member member) {
        List<ChatRoom> roomList = chatRoomRepository.findAllByHostIdOrGuestIdOrderByModifiedAtDesc(member.getId(), member.getId());
        List<ChatRoomListResponseDto> dto = new ArrayList<>();
        for (ChatRoom room : roomList) {
            String lastChat = "";
            if (room.getChatMessages().size() > 0) {
                lastChat = room.getChatMessages().get(room.getChatMessages().size() - 1).getMessage();
            }
            String partner = room.getHost().getNickname().equals(member.getNickname()) ? room.getGuest().getNickname() : room.getHost().getNickname();
            String profileImage = room.getHost().getNickname().equals(member.getNickname()) ? room.getGuest().getProfileImage() : room.getHost().getProfileImage();
            String postName = room.getCatchPost() == null ? "missing-room" : "catch-room";
            Long postId = room.getCatchPost() == null ? room.getMissingPost().getId() : room.getCatchPost().getId();
            String roomName = room.getCatchPost() == null ? room.getMissingPost().getKindCd() : room.getCatchPost().getKindCd();
            dto.add(ChatRoomListResponseDto.of(room, partner, lastChat, profileImage, postName, postId, roomName));
        }

        return ResponseDto.toResponseEntity(SuccessMessage.Chat_Room_List_SUCCESS, dto);
    }

    @Transactional
    public String createCatchRoom(Long postId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (post.getMember().getId().equals(member.getId())) {
            throw new CustomException(CREATE_CHAT_ROOM_EXCEPTION);
        }
        ChatRoom room = chatRoomRepository.findChatRoomByCatchPostIdAndHostIdAndGuestId(post.getId(), post.getMember().getId(), member.getId()).orElse(
                ChatRoom.of(post, member));
        chatRoomRepository.save(room);

        return room.getRoomId();
    }

    @Transactional
    public String createMissingRoom(Long postId, Member member) {
        PetPostMissing post = petPostMissingRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (post.getMember().getId().equals(member.getId())) {
            throw new CustomException(CREATE_CHAT_ROOM_EXCEPTION);
        }
        ChatRoom room = chatRoomRepository.findChatRoomByMissingPostIdAndHostIdAndGuestId(post.getId(), post.getMember().getId(), member.getId()).orElse(
                ChatRoom.of(post, member));
        chatRoomRepository.save(room);

        return room.getRoomId();
    }
}
