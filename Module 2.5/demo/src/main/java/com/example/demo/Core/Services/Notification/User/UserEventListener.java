package com.example.demo.Core.Services.Notification.User;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.Core.Beans.Kafka.User.UserConfig;
import com.example.demo.Core.DataModels.Events.User.UserEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
public class UserEventListener{
    private CountDownLatch latch = new CountDownLatch(1);
    private UserEvent receivedEvent;
    private ApplicationEventPublisher publisher;

    @KafkaListener(
        topics=UserConfig.USERTOPIC, 
        groupId=UserConfig.USERGROUP, 
        containerFactory=UserConfig.LISTENERCONTAINERFACTORY)
    public void listenUserEvents(UserEvent event){
        this.receivedEvent = event;
        this.latch.countDown();
        
        System.out.println("Recieving data : " + event.toString());
        System.out.print("--------------------------------");

        publisher.publishEvent(event);
    }
}
