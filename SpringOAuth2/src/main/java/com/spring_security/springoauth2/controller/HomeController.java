package com.spring_security.springoauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String greet()
    {
        return "Welcome to OAuth2 Security";
    }
}
