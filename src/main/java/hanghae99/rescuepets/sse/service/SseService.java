package hanghae99.rescuepets.sse.service;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Notification;
import hanghae99.rescuepets.common.entity.NotificationType;
import hanghae99.rescuepets.sse.dto.NotificationResponseDto;
import hanghae99.rescuepets.sse.repository.EmitterRepository;
import hanghae99.rescuepets.sse.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SseService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Member member) {
        String emitterId = makeTimeIncludeId(member.getId());
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(60 * 1000L));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(member.getId());
        sendToClient(emitter, eventId, emitterId, "EventStream Created. [userId = " + member.getId() + "]");

        return emitter;
    }

    @Transactional
    public void send(Member receiver, NotificationType notificationType, String message) {
        Notification notification = notificationRepository.save(Notification.createNotification(receiver, notificationType, message));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();

        Map<String, SseEmitter> sseEmitterMap = emitterRepository.findAllStartWithByMemberId(receiverId);
        sseEmitterMap.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, eventId, key, NotificationResponseDto.of(notification));
                }
        );
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
        }
    }
}