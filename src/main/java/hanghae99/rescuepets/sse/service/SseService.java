package hanghae99.rescuepets.sse.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Notification;
import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.sse.dto.NotificationResponseDto;
import hanghae99.rescuepets.sse.repository.EmitterRepository;
import hanghae99.rescuepets.sse.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.CONNECTION_ERROR;

@Service
@RequiredArgsConstructor
public class SseService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    private static final Long DEFAULT_TIMEOUT = 60 * 1000L;

    public SseEmitter subscribe(Member member) {
        String id = member.getId() + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));
        sendToClient(emitter, id, "EventStream Created. [userId = " + member.getId() + "]");

        return emitter;
    }

    public void send(Member receiver, NotificationType notificationType, String message) {
        Notification notification = notificationRepository.save(Notification.createNotification(receiver, notificationType, message));
        Map<String, SseEmitter> sseEmitterMap = emitterRepository.findAllStartWithById(String.valueOf(receiver.getId()));
        sseEmitterMap.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, NotificationResponseDto.of(notification));
                }
        );
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(id);
            throw new CustomException(CONNECTION_ERROR);
        }
    }
}