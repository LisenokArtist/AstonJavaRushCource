package com.example.DataModels.Models.User;

import com.example.DataModels.Entities.User.User;

import lombok.Getter;

@Getter
public class UserShort{
    private final int id;
    private final String name;

    public UserShort(User e){
        this.id = e.getId();
        this.name = e.getName();
    }
}
