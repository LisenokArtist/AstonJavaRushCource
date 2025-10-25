package com.example.datamodels.models.user;

import com.example.datamodels.entities.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreate {
    private String name;
    private int age;
    private String email;

    public UserCreate(User user){
        this.name = user.getName();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}
