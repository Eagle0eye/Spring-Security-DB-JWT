package com.spring_security.demo.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserPerController {
    @GetMapping
    public String get()
    {return "User::Get Method";}


    @PostMapping
    public String post()
    {return "User::Post Method";}

    @PutMapping
    public String put()
    {return "User::Put Method";}

    @DeleteMapping
    public String delete()
    {return "User::Delete Method";}
}
