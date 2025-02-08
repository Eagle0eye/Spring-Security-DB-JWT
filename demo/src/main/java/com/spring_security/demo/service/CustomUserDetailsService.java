package com.spring_security.demo.service;

import com.spring_security.demo.model.User;
import com.spring_security.demo.model.UserPrinciple;
import com.spring_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    CustomUserDetailsService(UserRepository repository)
    {
        this.userRepository=repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user==null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserPrinciple(user);
    }

}
