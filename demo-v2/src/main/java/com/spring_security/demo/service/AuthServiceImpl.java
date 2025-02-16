package com.spring_security.demo.service;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authManager;

    private final JWTServiceImpl jwtService;

    AuthServiceImpl(UserRepository userRepository,AuthenticationManager authManager,JWTServiceImpl jwtService)
    {
        this.userRepository =userRepository;
        this.authManager = authManager;
        this.jwtService =jwtService;

    }

//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public String verify(UserDTO user) {
        if (user==null)
            throw new RuntimeException("Not Completed");
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
                );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    @Override
    public void register(UserDTO userDTO){
        if(
            userDTO.getUsername().isEmpty() || userDTO.getPassword().isEmpty() || userDTO.getRoles().name().isEmpty()
        )
        {
            throw new RuntimeException("Enter Correct Information.");
        }
        try {
            User user =new User(userDTO.getUsername(), encoder.encode(userDTO.getPassword()), userDTO.getRoles());
            userRepository.save(user);
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Invalid username or password");
        }

    }



}
