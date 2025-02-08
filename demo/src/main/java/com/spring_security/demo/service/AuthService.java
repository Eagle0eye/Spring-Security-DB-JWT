package com.spring_security.demo.service;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;

public interface AuthService {
    void register(UserDTO userDTO);
    String verify(UserDTO user);

}
