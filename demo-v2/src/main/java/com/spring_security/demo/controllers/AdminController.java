package com.spring_security.demo.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping
    public String get()
    {return "Admin::Get Method";}


    @PostMapping
    public String post()
    {return "Admin::Post Method";}

    @PutMapping
    public String put()
    {return "Admin::Put Method";}

    @DeleteMapping
    public String delete()
    {return "Admin::Delete Method";}
}
