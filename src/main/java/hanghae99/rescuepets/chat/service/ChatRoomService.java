package hanghae99.rescuepets.chat.service;

import hanghae99.rescuepets.chat.dto.ChatRoomListResponseDto;
import hanghae99.rescuepets.chat.dto.ChatRoomResponseDto;
import hanghae99.rescuepets.chat.repository.ChatMemberRepository;
import hanghae99.rescuepets.chat.repository.ChatRoomRepository;
import hanghae99.rescuepets.common.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final PetPostCatchRepository petPostCatchRepository;
    private final PetPostMissingRepository petPostMissingRepository;

    public List<ChatRoomListResponseDto> getRoomList() {
        List<ChatMember> chatMembers = chatMemberRepository.findAllByMember(member);
        List<ChatRoomListResponseDto> dto = new ArrayList<>();
        for (ChatMember chatMember : chatMembers) {
            dto.add(ChatRoomListResponseDto.of(chatMember));
        }

        return dto;
    }

    public String createCatchRoom(Long postId, Member member) {
        PetPostCatch post = petPostCatchRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (member.getId().equals.(post.getMember().getId())) {
            throw new IllegalArgumentException("자신과 채팅 X");
        }

        Optional<ChatMember> chatMember = chatMemberRepository.findByCatchPostAndMember(post, member);
        if (chatMember.isPresent()) {
            return chatMember.get().getRoom().getRoomId();
        }

        ChatRoom chatRoom = ChatRoom.createRoom(post);
        chatRoomRepository.save(chatRoom);

        ChatMember owner = ChatMember.createCatch(post.getMember(), post, chatRoom, member.getNickName());
        ChatMember guest = ChatMember.createCatch(member, post, chatRoom, post.getMember().getNickname());
        List<ChatMember> chatMembers = new ArrayList<>();
        chatMembers.add(owner);
        chatMembers.add(guest);
        chatMemberRepository.saveAll(chatMembers);

        return chatRoom.getRoomId();
    }

    public String createMissingRoom(Long postId, Member member) {
        PetPostCatch post = petPostMissingRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (member.getId().equals.(post.getMember().getId())) {
            throw new IllegalArgumentException("자신과 채팅 X");
        }

        Optional<ChatMember> chatMember = chatMemberRepository.findByMissingPostAndMember(post, member);
        if (chatMember.isPresent()) {
            return chatMember.get().getRoom().getRoomId();
        }

        ChatRoom chatRoom = ChatRoom.createRoom(post);
        chatRoomRepository.save(chatRoom);

        ChatMember owner = ChatMember.createMissing(post.getMember(), post, chatRoom, member.getNickName());
        ChatMember guest = ChatMember.createMissing(member, post, chatRoom, post.getMember().getNickname());
        List<ChatMember> chatMembers = new ArrayList<>();
        chatMembers.add(owner);
        chatMembers.add(guest);
        chatMemberRepository.saveAll(chatMembers);

        return chatRoom.getRoomId();
    }

    public ChatRoomResponseDto getRoom(String roomId, Member member) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(NullPointerException::new);
        ChatMember chatMember = chatMemberRepository.findByRoomAndMember(chatRoom, member).orElseThrow(NullPointerException::new);
        return ChatRoomResponseDto.of(chatRoom, chatMember.getPartner());
    }

}
