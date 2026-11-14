package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.entity.User;
import com.github.matheusmarqs1.study_slot.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
