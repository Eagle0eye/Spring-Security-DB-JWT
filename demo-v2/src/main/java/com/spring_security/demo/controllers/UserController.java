package com.spring_security.demo.controllers;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;
import com.spring_security.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spring_security.demo.enums.Roles.ADMIN;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @PreAuthorize("(hasAuthority('admin:read') or hasAuthority('user:read'))")
    @GetMapping("/users/{id}")
    public UserDTO viewUserDetails(@PathVariable int id){
        return userService.ViewUser(id);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<User> viewUsers()
    {
        return userService.viewUsers();
    }

}
