package com.example.DataModels.Events.User;

import java.time.LocalDateTime;

import com.example.DataModels.Entities.User.User;

import lombok.Getter;

@Getter
public class UserEvent {
    private UserEventType eventType;
    private String userEmail;
    private String message;

    public UserEvent(){}

    public UserEvent(User u, UserEventType e){
        this.eventType = e;
        this.userEmail = u.getEmail();
        this.message = createMessage(e, u);
    }


    public static enum UserEventType {
        CREATE,
        DELETE
    }

    private static String createMessage(UserEventType e, User u){
        switch (e){
            case CREATE -> {
                return String.format("User %s has been created at %s", u.getName(), u.getCreatedAt());
            }
            case DELETE -> {
                return String.format("User %s has been deleted at %s", u.getName(), LocalDateTime.now());
            }
            default -> {
                return "Not implement event type";
            }
        }
    }
}
