package com.framework.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/home")          // ★ 정확히 이 경로를 매핑합니다
    public String home() {
        log.info("메인페이지 요청");
        return "Hello, SW Framework!";
    }
}
