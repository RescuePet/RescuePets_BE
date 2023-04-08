package hanghae99.rescuepets.sse.repository;

import hanghae99.rescuepets.common.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}