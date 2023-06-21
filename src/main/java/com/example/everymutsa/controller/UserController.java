package com.example.everymutsa.controller;

import com.example.everymutsa.domain.dto.UserDTO;
import com.example.everymutsa.domain.entity.User;
import com.example.everymutsa.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserSerivce userSerivce;

    @GetMapping("/users")
    public List<User> readUserList(){
        return userSerivce.findAll();
    }


}
