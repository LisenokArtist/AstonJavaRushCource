package com.example.Services.User;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.Configuration.User.UserConfig.LISTENERCONTAINERFACTORY;
import static com.example.Configuration.User.UserConfig.USERGROUP;
import static com.example.Configuration.User.UserConfig.USERTOPIC;
import com.example.DataModels.Events.User.UserEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
public class UserEventListener{
    private final CountDownLatch latch = new CountDownLatch(1);
    private UserEvent receivedEvent;
    private ApplicationEventPublisher publisher;

    @KafkaListener(
        topics=USERTOPIC, 
        groupId=USERGROUP, 
        containerFactory=LISTENERCONTAINERFACTORY)
    public void listenUserEvents(UserEvent event){
        this.receivedEvent = event;
        this.latch.countDown();
        
        System.out.println("Recieving data : " + event.toString());
        System.out.print("--------------------------------");

        publisher.publishEvent(event);
    }
}
