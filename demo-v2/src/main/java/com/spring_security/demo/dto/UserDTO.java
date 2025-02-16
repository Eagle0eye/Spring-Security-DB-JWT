package com.spring_security.demo.dto;

import com.spring_security.demo.enums.Roles;

public class UserDTO {
    private String username;
    private String password;
    private String roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Roles getRoles() {
            return Roles.valueOf(roles.toUpperCase());
    }
}
