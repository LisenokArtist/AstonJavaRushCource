package com.example.services.user;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserEvent;
import com.example.datamodels.models.user.UserEvent.UserEventType;

@Component
public class UserEventSender {
    @Autowired
    private final KafkaTemplate<String, UserEvent> userTemplate;
    @Autowired
    private final NewTopic userTopic;


    public UserEventSender(KafkaTemplate<String, UserEvent> userTemplate, NewTopic userTopic){
        this.userTemplate = userTemplate;
        this.userTopic = userTopic;
    }


    public KafkaTemplate<String, UserEvent> getUserTemplate(){
        return this.userTemplate;
    }

    public void sendEventCreate(User user){
        this.sendEvent(user, UserEventType.CREATE);
    }

    public void sendEventDelete(User user){
        this.sendEvent(user, UserEventType.DELETE);
    }

    private void sendEvent(User user, UserEventType type){
        UserEvent event = new UserEvent(user, type);
        this.send(event);
    }
    
    public void send(UserEvent event){
        System.out.print("Sending data : " + event.toString());
        System.out.print("--------------------------------");
        userTemplate.send(userTopic.name(), event);
    }
}

