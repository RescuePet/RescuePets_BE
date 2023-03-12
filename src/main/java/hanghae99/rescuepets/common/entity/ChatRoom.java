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

    @ManyToOne
    private PetPostCatch catchPost;

    @ManyToOne
    private PetPostMissing missingPost;

    public static ChatRoom createRoom(PetPostCatch post) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(post.getTitle)
                .catchPost(post)
                .build();
    }

    public static ChatRoom createRoom(PetPostMissing post) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(post.getTitle)
                .catchPost(post)
                .build();
    }
}
