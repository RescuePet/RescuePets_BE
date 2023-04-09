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

    private static final Long DEFAULT_TIMEOUT = 10 * 1000L;

    public SseEmitter subscribe(Member member) {
        String emitterId = makeTimeIncludeId(member.getId());
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(member.getId());
        sendToClient(emitter, eventId, emitterId, "EventStream Created. [userId = " + member.getId() + "]");

        return emitter;
    }

    public void send(Member member, NotificationType notificationType, String message) {
        Notification notification = notificationRepository.save(Notification.createNotification(member, notificationType, message));

        String receiverId = String.valueOf(member.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();

        Map<String, SseEmitter> sseEmitterMap = emitterRepository.findAllStartWithByMemberId(eventId);
        sseEmitterMap.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, eventId, key, NotificationResponseDto.of(notification));
                }
        );
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventID, Long memberId, String emitterId, SseEmitter emitter) {

        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventID.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendToClient(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private String makeTimeIncludeId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendToClient(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            throw new CustomException(CONNECTION_ERROR);
        }
    }
}