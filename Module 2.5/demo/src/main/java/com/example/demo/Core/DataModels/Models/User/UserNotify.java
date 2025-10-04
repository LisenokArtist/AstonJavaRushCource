package com.example.demo.Core.DataModels.Models.User;

import lombok.Getter;

@Getter
public class UserNotify {
    private int id;
    private String message;

    public UserNotify(int id, String message){
        this.id = id;
        this.message = message;
    }
}
