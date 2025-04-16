# 🧑‍💬 ChatWithRedis - 익명 실시간 채팅 서비스

> Java 17, Spring Boot, Redis, WebSocket 기반의 실시간 익명 채팅 서버입니다.

---

## 🚀 프로젝트 소개

이 프로젝트는 Redis의 **Pub/Sub** 기능을 활용하여,
다수의 클라이언트가 접속한 상태에서 **실시간으로 채팅 메시지를 주고받을 수 있는** 시스템입니다.

서버는 WebSocket + Redis Pub/Sub 기반으로 메시지를 송수신하며,  
클라이언트는 간단한 HTML과 JavaScript만으로 연결됩니다.

---

## 🔧 기술 스택

| 기술 | 설명 |
|------|------|
| Java 17 | 백엔드 언어 |
| Spring Boot 3 | 애플리케이션 프레임워크 |
| Redis | 메시지 브로커 (Pub/Sub + 사용자 세션 관리) |
| WebSocket (STOMP) | 실시간 통신 |
| SockJS + STOMP.js | 클라이언트 WebSocket 연결 |
| Maven | 빌드 도구 |
| Lombok | 코드 간결화를 위한 어노테이션 지원 |

---

## 📁 프로젝트 구조

```
src
└── main
    └── java
        └── org.chatwithredis
            ├── config
            │   ├── RedisConfig.java
            │   ├── WebConfig.java
            │   └── WebSocketConfig.java
            ├── controller
            │   ├── ChatController.java
            │   └── RoomController.java
            ├── model
            │   └── ChatMessage.java
            ├── redis
            │   ├── RedisCleaner.java
            │   ├── RedisPublisher.java
            │   └── RedisSubscriber.java
            └── ChatWithRedisApplication.java
```

---

## ✨ 주요 기능

- ✅ **닉네임 설정 기능 추가**
  - 기본적으로 서버에서 부여한 `익명1`, `익명2` 형태의 이름 사용
  - 사용자가 직접 닉네임을 설정할 수 있으며, 채팅창 상단에 표시됨
  - 닉네임 변경 시 채팅창에 시스템 메시지로 알림 표시
  - `닉네임 변경` 버튼 UI는 기존 테마와 어울리도록 커스터마이징

- ✅ **WebSocket 기반 실시간 채팅**
  - `SockJS + STOMP.js`를 사용한 WebSocket 연결
  - 클라이언트 간 실시간 채팅 메시지 송수신

- ✅ **Redis Pub/Sub 구조**
  - 서버 간 메시지 브로드캐스트를 Redis가 중개
  - 고성능 실시간 채팅 구현

- ✅ **방 ID 기반 채팅 분리**
  - `roomId`로 채팅방 구분
  - 사용자마다 독립적인 채널로 메시지를 주고받음

- ✅ **입장 순서 기반 익명 번호 부여**
  - `익명1`, `익명2`... 형식의 이름을 서버에서 부여
  - Redis를 통해 사용자 번호를 일관성 있게 관리

- ✅ **퇴장 시 번호 재사용 처리**
  - 퇴장하면 번호를 Redis에 반납 (`free` 리스트)
  - 다음 입장자가 해당 번호를 재사용함

- ✅ **입장/퇴장 메시지 출력**
  - 입장/퇴장 시 WebSocket으로 시스템 메시지 전송
  - 채팅창에 `익명3님이 퇴장하셨습니다.` 등 표시

- ✅ **실시간 접속자 수 표시**
  - `roomId` 기준 Redis Set에서 현재 인원 수 계산
  - 클라이언트 상단에 `(현재 3명)` 등 표시됨

- ✅ **서버 시작 시 Redis 초기화**
  - `room:*` 관련 키는 서버 실행 시 자동 삭제
  - 테스트 및 재시작 시 데이터 일관성 확보

- ✅ **클라이언트 UI 제공**
  - HTML/CSS 기반 채팅창 + 채팅방 목록
  - Enter 키 전송, 메시지 스크롤, 자동 스크롤 등 UI 개선

---

## ⚙️ 실행 방법

### 🔸 1. Redis 실행 (Docker)

```bash
docker run --name redis -p 6379:6379 -d redis
```

---

### 🔸 2. 서버 실행

```bash
./mvnw spring-boot:run
```

---

### 🔸 3. 클라이언트 테스트

브라우저에서:

```
http://localhost:8080/chat.html
```

여러 탭을 열고 입장 순서 및 퇴장 처리를 확인해보세요!

---

## 🛠️ 개발 시 유의 사항

- `Lombok` 플러그인 설치 필요 (`Annotation Processing` 활성화)
- `application.properties`에서 Redis 설정 확인
- `beforeunload`, `sendBeacon` 등 브라우저 이벤트 제약 사항 주의
- WebSocket 연결 해제 시 추가 처리 가능 (`SessionDisconnectEvent` 등)

---

## 🙋‍♂️ 개선 아이디어 (To-do)

- 채팅방 목록 API + 유저 리스트 표시
- 메시지 로그 Redis 저장 또는 DB 영속화
- JWT 기반 인증 처리
- 닉네임 설정 기능
- 채팅방 생성/삭제 기능

---

## 📜 라이선스

MIT License. 자유롭게 사용하고 수정하세요.