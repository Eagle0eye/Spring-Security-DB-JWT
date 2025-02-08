package com.spring_security.demo.controllers;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;
import com.spring_security.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;


    @GetMapping("/")
    public String greeting(Model model, HttpServletRequest request) {
        model.addAttribute("employee",request.getSession().getId());
        return "hello";
    }



    @PostMapping("/register")
    public void register(@RequestBody UserDTO form)
    {   service.register(form); }


    @PostMapping("/login")
    public String login(@RequestBody UserDTO form)
    {
        System.out.println(form.toString());
        return service.verify(form);
    }
}
