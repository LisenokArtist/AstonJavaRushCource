package com.example.datamodels.models.user;

import org.springframework.hateoas.RepresentationModel;

import com.example.datamodels.entities.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserShort extends RepresentationModel<UserShort> {
    private final int id;
    private final String name;

    public UserShort(User e){
        this.id = e.getId();
        this.name = e.getName();
    }
}
