package com.example.Configuration.User;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.DataModels.Events.User.UserEvent;

@Configuration
public class UserConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    public static String BOOTSTRAPSERVERS;
    public static final String USERTOPIC = "user-events";
    public static final String USERGROUP = "user-event-consumer-group";
    public static final String LISTENERCONTAINERFACTORY = "userListenerContainerFactory";
    
    @Bean
    public NewTopic userTopic(){
        return TopicBuilder
            .name(USERTOPIC)
            .build();
    }
    
    @Bean
    public ProducerFactory<String, UserEvent> userProducerFactory(){
        Map<String, Object> props = new HashMap<>();
        System.out.print(BOOTSTRAPSERVERS);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, UserEvent> userTemplate(
        ProducerFactory<String, UserEvent> userProducer
    ){
        return new KafkaTemplate<>(userProducer);
    }

    @Bean
    public ConsumerFactory<String, UserEvent> userConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, USERGROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.DataModels");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name=LISTENERCONTAINERFACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, UserEvent> userListenerContainerFactory(
        ConsumerFactory<String, UserEvent> userConsumer
    ){
        ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumer);
        return factory; 
    }
}
