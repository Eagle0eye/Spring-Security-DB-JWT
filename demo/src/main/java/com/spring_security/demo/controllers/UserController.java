package com.spring_security.demo.controllers;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;
import com.spring_security.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;



    @GetMapping("/users/{id}")
    public UserDTO viewUserDetails(@PathVariable int id){
        return userService.ViewUser(id);
    }

    @GetMapping("/users")
    public List<User> viewUsers()
    {
        return userService.viewUsers();
    }

}
