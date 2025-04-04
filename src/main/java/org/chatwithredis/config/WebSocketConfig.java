package org.chatwithredis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트가 메시지를 보낼 endpoint 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")  // WebSocket 연결 endpoint
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS();                 // SockJS fallback 지원
    }

    // 메시지 브로커 구성
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");    // 구독 (subscribe) prefix
        registry.setApplicationDestinationPrefixes("/pub");  // 발행 (publish) prefix
    }
}
