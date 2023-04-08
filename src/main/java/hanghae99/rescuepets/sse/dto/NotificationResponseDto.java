package hanghae99.rescuepets.sse.dto;

import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.common.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponseDto {
    private Long notificationId;
    private String message;
    private NotificationType notificationType;
    private Boolean isRead;

    public static NotificationResponseDto of(Notification notification) {
        return NotificationResponseDto.builder()
                .notificationId(notification.getId())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .isRead(notification.getIsRead())
                .build();
    }
}