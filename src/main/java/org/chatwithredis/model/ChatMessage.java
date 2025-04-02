package org.chatwithredis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private MessageType type;  // 메시지 타입 (입장, 채팅, 퇴장)
    private String roomId;     // 채팅방 ID
    private String sender;     // 보낸 사람
    private String message;    // 실제 메시지 내용
}
