package com.example.demo.Core.Entities.User;

import lombok.Getter;

@Getter
public class UserUpdate {
    private int id;
    private String name;

    public UserUpdate(int id, String name){
        this.id = id;
        this.name = name;
    }
}
