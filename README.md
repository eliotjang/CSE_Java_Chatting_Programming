## 소켓서버 방식 자바 채팅 프로그래밍

### 사용한 라이브러리 및 클래스

- **통신 프로토콜**: TCP/IP, UDP
- **입출력 및 파일 전송**: 입출력 스트림, URLConnection 클래스
- **네트워크 주소 처리**: InetAddress, URL 클래스
- **멀티캐스팅**: 멀티캐스팅 전송 방식
- **GUI 프로그래밍**: 이벤트 기반 GUI 프로그래밍, 스레드 처리

### 주요 특징

- **다양한 통신 프로토콜 활용**
    - TCP/IP 및 UDP를 이용하여 데이터 통신
- **파일 전송 기능**
    - 입출력 스트림과 URLConnection 클래스로 파일 전송
- **멀티캐스팅 지원**
    - 멀티캐스팅을 활용하여 다수 클라이언트에게 동시 데이터 전송
- **이벤트 기반 GUI 프로그래밍**
    - 스레드와 이벤트 처리로 사용자 친화적 GUI 인터페이스 구현
- **고급 네트워크 프로그래밍 클래스**
    - InetAddress와 URL 클래스 사용

### 주요 소스코드 구현
- [ChatClient.Java](src/chattingprogramming/ChatClient.java)
- [ChatServer.Java](src/chattingprogramming/ChatServer.java)
