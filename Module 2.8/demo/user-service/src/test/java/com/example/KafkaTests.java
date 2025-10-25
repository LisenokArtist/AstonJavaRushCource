package com.example;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import static org.assertj.core.api.Assertions.assertThat;
import org.awaitility.Awaitility;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserEvent;
import com.example.datamodels.models.user.UserEvent.UserEventType;
import com.example.services.UserEventSender;
import com.example.services.user.UserEventListener;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
class KafkaTests {
	@TestConfiguration // Or @Configuration for broader usage
    static class TestConfig {
		@Value("${spring.kafka.bootstrap-servers}")
		private String BOOTSTRAPSERVERS;
		@Value("${spring.kafka.user.group}")
		private String USERGROUP;
		@Bean
		public ConsumerFactory<String, UserEvent> userConsumerFactory(){
			Map<String, Object> props = new HashMap<>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVERS);
			props.put(ConsumerConfig.GROUP_ID_CONFIG, USERGROUP);
			props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
			props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
			return new DefaultKafkaConsumerFactory<>(props);
		}

		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, UserEvent> userListenerContainerFactory(
			ConsumerFactory<String, UserEvent> userConsumer
		){
			ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(userConsumer);
			return factory; 
		}
    }

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