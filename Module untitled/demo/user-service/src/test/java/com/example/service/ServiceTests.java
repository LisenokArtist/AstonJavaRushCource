package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.DataModels.Entities.User.User;
import com.example.DataModels.Events.User.UserEvent;
import com.example.DataModels.Models.User.UserShort;
import com.example.Repositories.User.UserRepository;
import com.example.Services.User.UserEventListener;
import com.example.Services.UserService;

@SpringBootTest
class ServiceTests {
	@Autowired
    private UserService service;
    @Autowired
    private UserEventListener listener;

    @BeforeEach
    void beforeEach(){
        fillDataBaseIfEmpty(service);
    }

    @Test
    void shouldSaveAndReportToKafka() throws InterruptedException{
        User user = createUsers(1).get(0);
        
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

	private static void fillDataBaseIfEmpty(UserRepository repository){
        if (repository.count() <= 0){
            List<User> users = createUsers(10);
            repository.saveAll(users);
        }
    }

    private static void fillDataBaseIfEmpty(UserService service){
        if (service.count() <= 0){
            List<User> users = createUsers(10);
            service.saveAll(users);
        }
    }

	private static List<User> createUsers(int count){
        List<User> users = new ArrayList<>();
        int index = 0;
        while (index < count){
            User user = new User(
                String.format("User%d", index),
                19 + index, 
                String.format("user%d@mail.com", index));
            users.add(user);
            index ++;
        }
        return users;
    }
}
