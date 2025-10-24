package com.example.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.datamodels.entities.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
