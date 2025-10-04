package com.example.demo.Core.Services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.Core.DataModels.Events.User.UserEvent;
import com.example.demo.Core.Services.EmailService;
import com.example.demo.Core.Services.Notification.User.UserEventListener;

@Service
public class UserNotificationService{
    @Autowired
    private final UserEventListener listener;
    @Autowired
    private final EmailService emailService;

    public UserNotificationService(UserEventListener listener, EmailService emailService){
        this.listener = listener;
        this.emailService = emailService;
    }

    @EventListener
    public void onUserEventListener(UserEvent event){
        System.out.println("Handed onUserEventListener : " + event.toString());
        System.out.print("--------------------------------");

        String email = event.getUserEmail();
        switch (event.getEventType()){
            case CREATE -> emailService.sendAccountCreatedEmail(email);
            case DELETE -> emailService.sendAccountDeletedEmail(email);
        }
    }
}
