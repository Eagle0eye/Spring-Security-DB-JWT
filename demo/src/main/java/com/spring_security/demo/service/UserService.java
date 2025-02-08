package com.spring_security.demo.service;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;

import java.util.List;

public interface UserService {

    UserDTO ViewUser(int id);
    List<User> viewUsers();

}
