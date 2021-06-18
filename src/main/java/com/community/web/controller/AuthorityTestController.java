package com.community.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityTestController {
    @GetMapping("/google")
    public String google(){
        return "google";
    }

    @GetMapping("/kakao")
    public String kakao(){
        return "kakao";
    }
}
