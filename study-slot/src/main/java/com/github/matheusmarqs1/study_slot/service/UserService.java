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

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public User update(Long id, User user){
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    public void delete(Long id){
        if(!userRepository.existsById(id)) {
            throw new RuntimeException();
        }
        userRepository.deleteById(id);
    }
}
