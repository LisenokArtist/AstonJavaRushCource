package com.example.beans.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.datamodels.models.user.UserEvent;

@Configuration
@PropertySource("classpath:application.properties")
public class UserConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAPSERVERS;
    @Value("${spring.kafka.user.group}")
    private String USERGROUP;
    @Value("${spring.kafka.user.topic}")
    private String USERTOPIC;

    @Bean
    public NewTopic userTopic(){
        return TopicBuilder
            .name(USERTOPIC)
            .partitions(3) // Specify the number of partitions
            .replicas(1) 
            .build();
    }
    
    @Bean
    public ConsumerFactory<String, UserEvent> userConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, USERGROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        System.out.println(String.format("userConsumerFactory running on %s with group %s", BOOTSTRAPSERVERS, USERGROUP));
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
