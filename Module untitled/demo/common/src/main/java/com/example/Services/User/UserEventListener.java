package com.example.services.user;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.datamodels.events.user.UserEvent;

import lombok.Getter;

@Component
@Getter
public class UserEventListener{
    private UserEvent receivedEvent;

    @KafkaListener(
        topics="${spring.kafka.user.topic}", 
        groupId="${spring.kafka.user.group}", 
        containerFactory="userListenerContainerFactory")
    public void listenUserEvents(UserEvent event){
        this.receivedEvent = event;
    }
}
