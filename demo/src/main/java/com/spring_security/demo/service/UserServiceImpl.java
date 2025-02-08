package com.spring_security.demo.service;

import com.spring_security.demo.dto.UserDTO;
import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository repository){
        this.userRepository =repository;
    }
    @Override
    public UserDTO ViewUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            User found_user = user.get();
            return new UserDTO(found_user.getUsername(),found_user.getPassword(),found_user.getRoles().name());
        }
        return null;
    }

    @Override
    public List<User> viewUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new RuntimeException("Not Found Information");
        return users;
    }







}
