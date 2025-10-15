package com.example.datamodels.models.user;

import com.example.datamodels.entities.user.User;

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
