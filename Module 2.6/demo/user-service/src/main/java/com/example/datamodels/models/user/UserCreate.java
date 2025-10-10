package com.example.datamodels.models.user;

import com.example.datamodels.entities.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreate {
    private final String name;
    private final int age;
    private final String email;

    public UserCreate(User user){
        this.name = user.getName();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}
