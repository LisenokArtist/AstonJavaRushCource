package com.example.demo;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.Core.DataModels.Entities.User.User;
import com.example.demo.Core.DataModels.Events.User.UserEvent;
import com.example.demo.Core.DataModels.Events.User.UserEvent.UserEventType;
import com.example.demo.Core.Services.Notification.User.UserEventListener;
import com.example.demo.Core.Services.Notification.User.UserEventSender;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
class KafkaTests {
	@Autowired
	private UserEventSender sender;
	@Autowired
	private UserEventListener listener;

	
	@Test
	void shouldSendMessageToKafkaAndReadIt() throws InterruptedException{
		User user = UnitTestUtils.createUsers(1).get(0);
		UserEvent event = new UserEvent(user, UserEventType.CREATE);
		
		sender.send(event);
		listener.getLatch().await(10, TimeUnit.SECONDS);
		
		assertThat(listener.getReceivedEvent().getEventType()).isEqualTo(event.getEventType());
		assertThat(listener.getReceivedEvent().getUserEmail()).isEqualTo(event.getUserEmail());
		assertThat(listener.getReceivedEvent().getMessage()).isEqualTo(event.getMessage());
	}
}