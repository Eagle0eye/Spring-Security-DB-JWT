package com.spring_security.demo.model;

import com.spring_security.demo.enums.Roles;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;


    public User(){};
    public User(String username,String password,Roles roles){
        this.username = username;
        this.password =password;
        this.roles =roles;
    }
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRoles() {
        return roles;
    }
}
