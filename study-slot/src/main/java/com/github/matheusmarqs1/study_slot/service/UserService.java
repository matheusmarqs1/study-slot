package com.github.matheusmarqs1.study_slot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.matheusmarqs1.study_slot.dto.request.CreateUserRequest;
import com.github.matheusmarqs1.study_slot.dto.request.UpdateUserRequest;
import com.github.matheusmarqs1.study_slot.dto.response.UserResponse;
import com.github.matheusmarqs1.study_slot.entity.User;
import com.github.matheusmarqs1.study_slot.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> findAll(){
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse findById(Long id){
        return UserResponse.from(userRepository.findById(id).orElseThrow());
    }

    public UserResponse create(CreateUserRequest request){
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .build();
        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse update(Long id, UpdateUserRequest request){
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setName(request.name());
        existingUser.setEmail(request.email());
        return UserResponse.from(userRepository.save(existingUser));
    }

    public void delete(Long id){
        if(!userRepository.existsById(id)) {
            throw new RuntimeException();
        }
        userRepository.deleteById(id);
    }
}
