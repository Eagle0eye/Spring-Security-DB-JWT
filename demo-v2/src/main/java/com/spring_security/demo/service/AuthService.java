package com.spring_security.demo.service;

import com.spring_security.demo.dto.UserDTO;


public interface AuthService {
    void register(UserDTO userDTO);
    String verify(UserDTO user);

}
