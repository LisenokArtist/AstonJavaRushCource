package com.example.demo.Core.Services.Notification.User;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.Core.DataModels.Entities.User.User;
import com.example.demo.Core.DataModels.Events.User.UserEvent;
import com.example.demo.Core.DataModels.Events.User.UserEvent.UserEventType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEventSender {
    @Autowired
    private final KafkaTemplate<String, UserEvent> userTemplate;
    @Autowired
    private final NewTopic userTopic;


    public UserEventSender(KafkaTemplate<String, UserEvent> userTemplate, NewTopic topic){
        this.userTemplate = userTemplate;
        this.userTopic = topic;
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
