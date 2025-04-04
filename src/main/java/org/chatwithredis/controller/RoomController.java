package org.chatwithredis.controller;

import lombok.RequiredArgsConstructor;
import org.chatwithredis.model.ChatMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{roomId}/join")
    public ResponseEntity<String> joinRoom(@PathVariable String roomId) {
        String freeKey = "room:" + roomId + ":free";
        String usedKey = "room:" + roomId + ":used";
        String nextKey = "room:" + roomId + ":next";

        Long assignedNumber;
        Object popped = redisTemplate.opsForList().leftPop(freeKey);

        if (popped != null) {
            assignedNumber = Long.parseLong(popped.toString());
            System.out.printf("✅ [입장] room:%s → 재사용 번호 익명%d 부여\n", roomId, assignedNumber);
        } else {
            assignedNumber = redisTemplate.opsForValue().increment(nextKey);
            System.out.printf("✅ [입장] room:%s → 새 번호 익명%d 부여\n", roomId, assignedNumber);
        }

        redisTemplate.opsForSet().add(usedKey, assignedNumber.toString());

        return ResponseEntity.ok("익명" + assignedNumber);
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Void> leaveRoom(@PathVariable String roomId, @RequestParam String sender) {
        String usedKey = "room:" + roomId + ":used";
        String freeKey = "room:" + roomId + ":free";

        if (sender.startsWith("익명")) {
            try {
                Long number = Long.parseLong(sender.substring(2));
                redisTemplate.opsForSet().remove(usedKey, number.toString());
                redisTemplate.opsForList().rightPush(freeKey, number.toString());

                System.out.printf("🚪 [퇴장] room:%s → 익명%d 퇴장 처리\n", roomId, number);

                // 퇴장 메시지 전송
                ChatMessage message = ChatMessage.builder()
                        .type(ChatMessage.MessageType.LEAVE)
                        .roomId(roomId)
                        .sender(sender)
                        .message(sender + "님이 퇴장하셨습니다.")
                        .build();

                messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, message);

            } catch (NumberFormatException e) {
                System.out.println("❌ [퇴장 오류] sender 형식이 잘못됨: " + sender);
            }
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{roomId}/users")
    public ResponseEntity<Long> getUserCount(@PathVariable String roomId) {
        String usedKey = "room:" + roomId + ":used";
        Long count = redisTemplate.opsForSet().size(usedKey); // 현재 유저 수
        return ResponseEntity.ok(count != null ? count : 0L);
    }
}
