package hanghae99.rescuepets.sse.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String id, SseEmitter sseEmitter);

    void saveEventCache(String id, Object event);

    Map<String, SseEmitter> findAllStartWithByMemberId(String id);

    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    void deleteById(String id);
}