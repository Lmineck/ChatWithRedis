package org.chatwithredis.controller;

import org.chatwithredis.model.ChatMessage;
import org.chatwithredis.redis.RedisPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ObjectMapper objectMapper;

    /**
     * 클라이언트에서 "/pub/chat/message"로 메시지를 보내면 실행됨
     */
    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessage message) {
        try {
            // ChatMessage 객체를 JSON 문자열로 변환
            String jsonMessage = objectMapper.writeValueAsString(message);

            // RedisPublisher를 통해 Redis 채널로 메시지 발행
            redisPublisher.publish(jsonMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
