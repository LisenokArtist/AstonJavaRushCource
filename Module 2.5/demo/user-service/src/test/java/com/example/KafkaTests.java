package com.example;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import org.awaitility.Awaitility;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserEvent;
import com.example.datamodels.models.user.UserEvent.UserEventType;
import com.example.services.user.UserEventListener;
import com.example.services.user.UserEventSender;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
class KafkaTests {
	@Autowired
	private UserEventSender sender;
	@Autowired
	private UserEventListener listener;

	@Test
	void shouldSendMessageToKafkaAndReadIt() throws InterruptedException{
		User user = createUsers(1).get(0);
		UserEvent event = new UserEvent(user, UserEventType.CREATE);
		
		sender.send(event);
		
		Awaitility
			.await()
			.atMost(10, TimeUnit.SECONDS)
			.pollDelay(Duration.ofSeconds(2))
			.ignoreExceptions()
			.until(() -> listener.getReceivedEvent().getEventType(), equalTo(UserEventType.CREATE));
		assertThat(listener.getReceivedEvent().getUserEmail()).isEqualTo(event.getUserEmail());
		assertThat(listener.getReceivedEvent().getMessage()).isEqualTo(event.getMessage());
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