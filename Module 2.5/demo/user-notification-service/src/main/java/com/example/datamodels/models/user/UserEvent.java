package com.example.datamodels.models.user;

import java.time.LocalDateTime;

import com.example.datamodels.entities.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEvent {
    private UserEventType eventType;
    private String userEmail;
    private String message;

    
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
