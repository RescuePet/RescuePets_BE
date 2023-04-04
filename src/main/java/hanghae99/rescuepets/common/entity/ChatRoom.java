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
public class ChatRoom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    List<Chat> chatMessages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member host;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member guest;

    public static ChatRoom of(Post post, Member member) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post)
                .host(post.getMember())
                .guest(member)
                .build();
    }
}
