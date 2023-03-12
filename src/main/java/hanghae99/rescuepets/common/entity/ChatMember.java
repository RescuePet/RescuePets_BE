package hanghae99.rescuepets.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partner;

    @ManyToOne
    private Member member;

    @ManyToOne
    private PetPostCatch catchPost;

    @ManyToOne
    private PetPostMissing missingPost;

    @ManyToOne
    private ChatRoom room;

    public static ChatMember createCatch(Member member, PetPostCatch post, ChatRoom chatRoom, String partner) {
        return ChatMember.builder()
                .member(member)
                .catchPost(post)
                .room(chatRoom)
                .partner(partner)
                .build();
    }

    public static ChatMember createMissing(Member member, PetPostMissing post, ChatRoom chatRoom, String partner) {
        return ChatMember.builder()
                .member(member)
                .missingPost(post)
                .room(chatRoom)
                .partner(partner)
                .build();
    }
}
