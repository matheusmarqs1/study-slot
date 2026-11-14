package com.github.matheusmarqs1.study_slot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.matheusmarqs1.study_slot.entity.User;
import com.github.matheusmarqs1.study_slot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
}
