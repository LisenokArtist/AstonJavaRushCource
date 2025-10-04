package com.example.demo;


import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.Core.DataModels.Entities.User.User;
import com.example.demo.Core.DataModels.Events.User.UserEvent;
import com.example.demo.Core.DataModels.Models.User.UserShort;
import com.example.demo.Core.Services.Notification.User.UserEventListener;
import com.example.demo.Core.Services.User.UserService;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class UserServiceTest {
    @Autowired
    private UserService service;
    @Autowired
    private UserEventListener listener;

    @BeforeEach
    void beforeEach(){
        UnitTestUtils.fillDataBaseIfEmpty(service);
    }

    @Test
    void shouldSaveAndReportToKafka() throws InterruptedException{
        User user = UnitTestUtils.createUsers(1).get(0);
        
        UserShort userShort = service.save(user);
        assertNotNull(userShort);
        assertThat(userShort.getName()).isEqualTo(user.getName());
        
        listener.getLatch().await(10, TimeUnit.SECONDS);
        assertThat(listener.getReceivedEvent().getEventType()).isEqualTo(UserEvent.UserEventType.CREATE);
        assertThat(listener.getReceivedEvent().getUserEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldDeleteAndReportToKafka() throws InterruptedException{
        UserShort userFromDB = service.findFirst();
        
        UserShort userShort = service.delete(userFromDB.getId());
        assertThat(userShort.getId()).isEqualTo(userFromDB.getId());
        
        listener.getLatch().await(10, TimeUnit.SECONDS);
        assertThat(listener.getReceivedEvent().getEventType()).isEqualTo(UserEvent.UserEventType.DELETE);
    }
}
