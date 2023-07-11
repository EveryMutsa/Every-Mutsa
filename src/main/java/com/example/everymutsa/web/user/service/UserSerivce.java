package com.example.everymutsa.web.user.service;

import com.example.everymutsa.web.user.domain.entity.User;
import com.example.everymutsa.web.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSerivce {
    private final UserRepository userRepository;

    public UserSerivce(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }
}
