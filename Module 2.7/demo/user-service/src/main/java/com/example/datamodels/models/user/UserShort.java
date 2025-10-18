package com.example.datamodels.models.user;

import org.springframework.hateoas.RepresentationModel;

import com.example.datamodels.entities.user.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description="Short details about a user")
public class UserShort extends RepresentationModel<UserShort> {
    @Schema(description="Unique identifier of the user", example="123")
    private final int id;
    
    @Schema(description = "User's full name", example = "John Doe", required = true)
    private final String name;

    public UserShort(User e){
        this.id = e.getId();
        this.name = e.getName();
    }
}
