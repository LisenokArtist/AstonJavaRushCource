package com.example.datamodels.models.user;

import lombok.Getter;

@Getter
public class UserNotify {
    private final int id;
    private final String message;

    public UserNotify(int id, String message){
        this.id = id;
        this.message = message;
    }
}
