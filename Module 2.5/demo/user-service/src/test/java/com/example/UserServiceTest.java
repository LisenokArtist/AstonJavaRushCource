package com.example;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import org.awaitility.Awaitility;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserEvent.UserEventType;
import com.example.datamodels.models.user.UserShort;
import com.example.repositories.user.UserRepository;
import com.example.services.user.UserEventListener;
import com.example.services.user.UserService;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class UserServiceTest {
    @Autowired
    private UserService service;
    @Autowired
    private UserEventListener listener;

    @BeforeEach
    @SuppressWarnings("unused")
    void beforeEach(){
        fillDataBaseIfEmpty(service);
    }

    @Test
    void shouldSaveAndReportToKafka() throws InterruptedException{
        User user = createUsers(1).get(0);
        
        UserShort userShort = service.save(user);
        assertNotNull(userShort);
        assertThat(userShort.getName()).isEqualTo(user.getName());
        
        Awaitility
			.await()
			.atMost(10, TimeUnit.SECONDS)
			.pollDelay(Duration.ofSeconds(2))
			.ignoreExceptions()
			.until(() -> listener.getReceivedEvent().getEventType(), equalTo(UserEventType.CREATE));
        assertThat(listener.getReceivedEvent().getUserEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldDeleteAndReportToKafka() throws InterruptedException{
        UserShort userFromDB = service.findFirst();
        
        UserShort userShort = service.delete(userFromDB.getId());
        assertThat(userShort.getId()).isEqualTo(userFromDB.getId());
        
        Awaitility
			.await()
			.atMost(10, TimeUnit.SECONDS)
			.pollDelay(Duration.ofSeconds(2))
			.ignoreExceptions()
			.until(() -> listener.getReceivedEvent().getEventType(), equalTo(UserEventType.DELETE));
        assertThat(listener.getReceivedEvent().getEventType()).isEqualTo(UserEventType.DELETE);
    }

    @SuppressWarnings("unused")
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
