package com.github.matheusmarqs1.study_slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.matheusmarqs1.study_slot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
