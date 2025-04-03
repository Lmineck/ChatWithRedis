package org.chatwithredis.redis;

import org.chatwithredis.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지를 수신하면 실행되는 메서드
     */
    public void onMessage(String message, String pattern) {
        try {
            // JSON 문자열을 ChatMessage 객체로 역직렬화
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

            // 메시지를 해당 채팅방 구독자에게 broadcast
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
