# 🧑‍💬 ChatWithRedis - 익명 실시간 채팅 서비스

> Java 17, Spring Boot, Redis, WebSocket 기반의 간단한 실시간 채팅 서버입니다.

---

## 🚀 프로젝트 소개

이 프로젝트는 Redis의 **Pub/Sub** 기능을 활용하여,
다수의 클라이언트가 접속한 상태에서 **실시간으로 채팅 메시지를 주고받을 수 있는** 시스템입니다.

클라이언트는 WebSocket을 통해 서버와 연결되며, 서버는 Redis 채널을 이용해 메시지를 브로드캐스트합니다.

---

## 🔧 기술 스택

| 기술 | 설명 |
|------|------|
| Java 17 | 백엔드 언어 |
| Spring Boot 3 | 애플리케이션 프레임워크 |
| Redis | 메시지 브로커 (Pub/Sub) |
| WebSocket (STOMP) | 실시간 통신 |
| SockJS + STOMP.js | 클라이언트 WebSocket 연결 |
| Maven | 빌드 도구 |
| Lombok | 코드 간결화를 위한 어노테이션 지원 |

---

## 📁 프로젝트 구조

```
src
└── main
    ├── java
    │   └── org.chatwithredis
    │       ├── config
    │       │   └── RedisConfig.java
    │       │   └── WebSocketConfig.java
    │       ├── controller
    │       │   └── ChatController.java
    │       ├── model
    │       │   └── ChatMessage.java
    │       ├── redis
    │       │   └── RedisPublisher.java
    │       │   └── RedisSubscriber.java
    │       ├── websocket
    │       │   └── WebSocketConfig.java
    │       ├── ChatWithRedisApplication.java
    └── resources
        ├── application.properties
        └── static
            └── chat.html
```

---

## ✨ 주요 기능

- ✅ **WebSocket(STOMP) 기반 실시간 채팅**
    - 클라이언트는 `SockJS + STOMP.js`를 사용하여 WebSocket 연결
    - 메시지를 서버에 전송하면, 서버는 이를 Redis를 통해 모든 사용자에게 브로드캐스트

- ✅ **Redis Pub/Sub 기반 메시지 전송**
    - 메시지는 `chatroom`이라는 Redis 채널을 통해 발행(publish)
    - 서버는 Redis를 구독(subscribe)하여 메시지를 수신하고 WebSocket을 통해 클라이언트에 전달

- ✅ **채팅방(roomId) 분리 지원**
    - 각 메시지는 특정 채팅방 ID(`roomId`)를 포함하여 전송됨
    - 클라이언트는 `roomId`별로 WebSocket 채널을 구독함

- ✅ **입장/퇴장 메시지 처리**
    - 사용자가 채팅방에 들어오거나 나갈 때 입장/퇴장 메시지를 자동으로 전송

- ✅ **익명 사용자 이름 자동 생성**
    - 클라이언트 접속 시 무작위 익명 이름(`익명123` 등) 자동 부여

- ✅ **클라이언트 HTML 제공**
    - 기본 HTML 파일(`chat.html`)을 통해 브라우저에서 테스트 가능
    - 메시지 입력, 출력 UI 제공

---

## ⚙️ 실행 방법

### 🔸 1. Redis 실행 (Docker)

```bash
docker run --name redis -p 6379:6379 -d redis
```

---

### 🔸 2. 서버 실행

```bash
# 프로젝트 루트에서
./mvnw spring-boot:run
```

---

### 🔸 3. 클라이언트 테스트

브라우저에서:

```
http://localhost:8080/chat.html
```

브라우저 탭 여러 개를 열고 메시지를 주고받아보세요!

---

## 🛠️ 개발 시 유의 사항

- `Lombok` 플러그인 설치 및 Annotation Processing 설정 필요
- `application.yml`에서 Redis 연결 정보(`localhost`, 포트 등) 확인
- 브라우저가 WebSocket을 지원하지 않으면 SockJS가 fallback

---

## 🙋‍♂️ 개선 아이디어 (To-do)

- 채팅방 목록 API 만들기
- 닉네임 설정 기능 추가
- 메시지 Redis 저장 또는 DB 연동
- 채팅방 입장/퇴장 사용자 관리
- JWT 등으로 인증 기능 추가

---

## 📜 라이선스

MIT License. 자유롭게 사용하고 수정하세요.