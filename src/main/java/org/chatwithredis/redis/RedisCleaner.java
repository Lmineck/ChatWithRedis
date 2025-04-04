package org.chatwithredis.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisCleaner {

    private final RedisTemplate<String, Object> redisTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void cleanRedisOnStartup() {
        Set<String> keys = redisTemplate.keys("room:*");

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            System.out.printf("🧹 Redis 초기화 완료 (%d개 키 삭제됨):\n%s\n", keys.size(), keys);
        } else {
            System.out.println("✅ 초기화할 Redis 키가 없습니다.");
        }
    }
}
