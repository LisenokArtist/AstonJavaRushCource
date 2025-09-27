package com.example.demo.Core.Entities.User;

import lombok.Getter;

@Getter
public class UserCreate {
    private String name;
    private int age;
    private String email;

    public UserCreate(String name, int age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
