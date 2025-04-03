package org.chatwithredis.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    /**
     * Redis로 메시지를 발행 (publish)
     */
    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
