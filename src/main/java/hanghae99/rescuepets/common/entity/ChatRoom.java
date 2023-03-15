package hanghae99.rescuepets.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String roomName;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    List<Chat> chatMessages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private PetPostCatch catchPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private PetPostMissing missingPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member host;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member guest;

//
//    public static ChatRoom createRoom(PetPostCatch post) {
//        return ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(post.getTitle)
//                .catchPost(post)
//                .build();
//    }
//
//    public static ChatRoom createRoom(PetPostMissing post) {
//        return ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(post.getTitle)
//                .catchPost(post)
//                .build();
//    }

    public static ChatRoom of(PetPostCatch post, Member member) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
//                .roomName(post.getTitle())
                .catchPost(post)
                .host(post.getMember())
                .guest(member)
                .build();
    }

    public static ChatRoom of(PetPostMissing post, Member member) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
//                .roomName(post.getTitle())
                .missingPost(post)
                .host(post.getMember())
                .guest(member)
                .build();
    }
}
