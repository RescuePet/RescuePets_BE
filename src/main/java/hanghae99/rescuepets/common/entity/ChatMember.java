package hanghae99.rescuepets.common.entity;

import javax.persistence.*;

@Entity
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer unreadMessageCount;
    private String partner;

}
