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
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private boolean isHostExited;

    private boolean isGuestExited;

    private int hostChatCount;

    private int guestChatCount;

    public static ChatRoom of(Post post, Member member) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .post(post)
                .host(post.getMember())
                .guest(member)
                .build();
    }

    public void setHostExited(boolean hostExited) {
        this.isHostExited = hostExited;
    }

    public void setGuestExited(boolean guestExited) {
        this.isGuestExited = guestExited;
    }

    public void setHostChatCount(int count) {
        this.hostChatCount = count;
    }

    public void setGuestChatCount(int count) {
        this.guestChatCount = count;
    }

    public void initHostChatCount() {
        this.hostChatCount = 0;
    }

    public void initGuestChatCount() {
        this.guestChatCount = 0;
    }

    public boolean isHost(Member member) {
        return getHost().getId().equals(member.getId());
    }
}