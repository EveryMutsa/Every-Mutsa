package com.example.everymutsa.member.service;

import com.example.everymutsa.member.domain.entity.User;
import com.example.everymutsa.member.repository.UserRepository;
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
