package com.example.datamodels.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdate {
    private final int id;
    private final String name;
}
