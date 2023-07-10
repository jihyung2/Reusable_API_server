package com.example.reusable_api_server.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//재사용성이 높은 API 서버 만들기
@SpringBootApplication //스프링 부트 애플리케이션임을 나타내는 어노테이션입니다. 스프링 부트의 자동 구성과 구성 요소 검색을 활성화합니다.
@RestController //RESTful API를 처리하는 컨트롤러 클래스임을 나타내는 어노테이션입니다. 해당 클래스의 메서드들은 API 엔드포인트로 사용됩니다.
@RequestMapping("/api") // /api 경로로 시작하는 모든 요청에 대해 이 컨트롤러가 처리한다고 지정합니다.
public class ReusableApiServer {

    @GetMapping("/getData") //GET 요청에 대한 핸들러 메서드입니다. /api/getData 경로로 들어오는 GET 요청을 처리합니다.
    public Mono<String> getDataFromBackend() { //리액티브 프로그래밍을 위한 Mono 클래스로, 비동기적으로 처리되는 문자열을 나타냅니다.
        String backendUrl = "http://127.0.0.1:8070/testdata";
        //스프링 5부터 도입된 비동기 HTTP 통신을 위한 클라이언트입니다. 다양한 HTTP 요청을 보내고 응답을 받을 수 있습니다.
        WebClient webClient = WebClient.create(); //WebClient 인스턴스를 생성하는 메서드입니다.

        webClient.get()
                .uri(backendUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(result -> {System.out.println(result);
                });

        return webClient.get()
                .uri(backendUrl)
                .retrieve()
                .bodyToMono(String.class);
    }
    @GetMapping("/getData/{type}") //GET 요청에 대한 핸들러 메서드입니다. /api/getData 경로로 들어오는 GET 요청을 처리합니다.
    public Mono<String> getDataFromBackend2(@PathVariable String type, @RequestBody Object body) { //리액티브 프로그래밍을 위한 Mono 클래스로, 비동기적으로 처리되는 문자열을 나타냅니다.

        if (type.equals("test")){
            System.out.println("성공입니다.");
        }

        String backendUrl = "http://127.0.0.1:8070/"+type;
        //스프링 5부터 도입된 비동기 HTTP 통신을 위한 클라이언트입니다. 다양한 HTTP 요청을 보내고 응답을 받을 수 있습니다.
        WebClient webClient = WebClient.create(); //WebClient 인스턴스를 생성하는 메서드입니다.

        webClient.get()
                .uri(backendUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(result -> {System.out.println(result);
                });

        return webClient.get()
                .uri(backendUrl)
                .retrieve()
                .bodyToMono(String.class);
    }


    //백엔드 서버로 GET 요청을 보내고 응답을 받습니다.
    // get() 메서드를 호출하여 GET 요청을 설정하고,
    // uri(backendUrl)을 사용하여 요청 URI를 설정합니다.
    // retrieve()를 호출하여 응답을 가져오고,
    // bodyToMono(String.class)를 사용하여 응답 본문을 Mono<String>으로 변환합니다.

    public static void main(String[] args) {
        SpringApplication.run(ReusableApiServer.class, args);
    }
}

// /api/getData 엔드포인트로 들어오는 GET 요청을 받아서 백엔드 서버로 전달하고,
// 백엔드 서버로부터 받은 응답을 비동기적으로 반환하는 API 서버를 구성합니다.






