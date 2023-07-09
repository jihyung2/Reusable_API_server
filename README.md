# Reusable_API_server
간단한 REST API 서버 만들기 (스프링부트) https://velog.io/@tjdals9638/Spring-boot%EB%A1%9C-Rest-API-%EB%A7%8C%EB%93%A4%EA%B8%B0

서버 처리 과정의 분리
1. controller: 클라이언트 요청을 받고 서비스에 처리를 요청, 이후 클라이언트에게 응답
2. service: controller의 호출에 따라 사용자의 요구사항을 처리, DB의 정보 등 필요에 따라 repository에 요청
3. repository: 데이터베이스 관련 처리 담당

# 위성, 캡스톤2, 개인 프로젝트 예약 챗봇에 공용으로 사용될 재사용성 Api 서버
  
Front <--Rest api--> backend api serve(java) <---Restapi---> main server

프론트에서 API 콜 하면 API 서버를 통해 데이터가 전송이 된다.

프론트엔드에서 해당 api 콜해서 쓰는방식

1. 백엔드 아키텍처 -> api 서버 형태의 서버, 서버를 활용해서 인터페이스가 챗봇, 웹 등 프론트랑 백엔드 서버를 잘 구분지어서 View부분이 달라져도 할 수 있게 구분하기

재사용성 높은 Api 서버 만드는게 주가 된다. 

## 내용
언어는 Java, 프레임워크는 스프링부트를 사용했다.
외부에서 API를 호출하는 방법으로 WebClient 방식 사용
설계 방식은 기존 파이썬 챗봇을 예시로 main 서버에서 커맨드 핸들러로 요청이 오면 해당 메인 코드의 커멘드 핸들러로 전송되어 데이터 처리가 이루어지는 과정을
파이썬 커맨드 헨들러 요청이 오면 api 콜을 한후 -> Reusable API Sever로 API요청이 온다. -> 받은 요청을 처리해주는 백엔드 서버로 요청을 전송하고 백엔드 서버로부터 받은 요청 데이터를 프론트로 전달해준다.
* 주의할점은 재사용성이 높은 API 아키텍처이기 때문에 포괄적으로 작성을 해야한다.

작성하면서 프론트와 백엔드 사이에 들어올 API이기 때문에 프론트에서는 어떤식으로 요청, 응답해야하고, 백에서는 어떤식으로 요청, 응답 하는 방식을 적을 예정이다.


## 코드
1. WebClient 방식 사용하기
WebClient는 비동기적인 HTTP 클라이언트입니다. WebClient를 사용하여 HTTP 요청을 보낼 때, 요청은 비동기적으로 전송되고 응답은 비동기적으로 받아옵니다. 이는 블로킹하지 않고 다른 작업을 수행할 수 있도록 합니다.

WebClient는 Reactor 프로젝트의 일부로서 Reactor의 Mono와 Flux 타입과 함께 사용됩니다. Mono는 0 또는 1개의 결과를 가질 수 있는 단일 값이고, Flux는 0부터 N개의 결과를 가질 수 있는 여러 값을 가집니다.

WebClient의 retrieve() 메서드는 Mono나 Flux를 반환하며, bodyToMono() 또는 bodyToFlux() 메서드를 사용하여 응답 본문을 Mono나 Flux로 변환할 수 있습니다. 이러한 리액티브 타입을 활용하여 비동기적으로 응답을 처리할 수 있습니다.

따라서 WebClient를 사용하면 비동기적으로 백엔드 서버와 통신할 수 있으며, 다른 작업을 동시에 수행할 수 있는 장점이 있습니다.

사용법
build.gradle에
implementation 'org.springframework.boot:spring-boot-starter-webflux' 넣기

2. Mono
Mono는 0-1개의 결과만을 처리하기 위한 Reactor 객체
Flux는 0-N개의 결과물을 처리하기 위한 Reactor 객체

3. 스프링 부트에서 API 서버의 IP 주소와 포트를 설정하는 방법은 application.properties 파일이나 application.yml 파일을 사용하여 할 수 있습니다.
server.address=0.0.0.0
server.port=8080

application.properties 파일에서 IP 주소와 포트 번호를 설정하면 스프링 부트 애플리케이션에서 자동으로 해당 주소와 포트에 연결됩니다. 이는 스프링 부트의 내장 웹 서버가 해당 설정을 인식하고 설정된 IP 주소와 포트 번호로 바인딩되기 때문입니다.

## 작동 방식

1. @GetMapping("/getData") 메서드는 /api/getData 경로로 들어오는 GET 요청을 처리합니다.
프론트로부터의 요청이 들어오면, getDataFromBackend() 메서드가 실행됩니다.
2. getDataFromBackend() 메서드 내에서는 WebClient를 사용하여 백엔드 서버에 GET 요청을 보냅니다.
backendUrl 변수에는 백엔드 서버의 URL인 "http://127.0.0.1/api/data"가 설정되어 있습니다.
webClient.get().uri(backendUrl)를 호출하여 백엔드 서버로 GET 요청을 보냅니다.
3. retrieve()를 호출하여 응답을 가져오고, bodyToMono(String.class)를 사용하여 응답 본문을 Mono<String>으로 변환합니다.
반환된 Mono<String>은 비동기적으로 응답을 받을 수 있는 객체입니다.
4. 최종적으로 Mono<String>을 반환합니다.
프론트로는 이 Mono<String> 객체를 비동기적으로 받아 처리할 수 있습니다.

결과적으로, 프론트에서 API 서버의 /api/getData 엔드포인트에 GET 요청을 보내면, API 서버는 백엔드 서버에 해당 데이터를 요청하고 응답을 받습니다. 그리고 해당 응답은 Mono<String> 형태로 프론트로 반환됩니다. 프론트는 이 Mono<String> 객체를 비동기적으로 받아서 원하는 방식으로 처리할 수 있습니다.


## 에러
1. 순환 참조 에러가 발생했음 

## 해결방법
1. @bean을 제거하고 그 문장을 합쳐서 해결함