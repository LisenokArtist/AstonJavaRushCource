package com.example.services.user;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.configuration.user.UserConfig.LISTENERCONTAINERFACTORY;
import static com.example.configuration.user.UserConfig.USERGROUP;
import static com.example.configuration.user.UserConfig.USERTOPIC;
import com.example.datamodels.events.user.UserEvent;

import lombok.Getter;

@Component
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
