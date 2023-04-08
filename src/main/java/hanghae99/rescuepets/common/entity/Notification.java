package hanghae99.rescuepets.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    private Member receiver;

    public void setIsRead() {
        this.isRead = true;
    }

    public static Notification createNotification(Member receiver, NotificationType notificationType, String message) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .message(message)
                .isRead(false)
                .build();
    }
}