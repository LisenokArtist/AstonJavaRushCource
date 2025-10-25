package com.example.beans.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.datamodels.models.user.UserEvent;

@Configuration
@EnableKafka
@PropertySource("classpath:application.properties")
public class UserProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAPSERVERS;
    @Value("${spring.kafka.user.topic}")
    private String USERTOPIC;

    @Bean
    public NewTopic userTopic(){
        return TopicBuilder
            .name(USERTOPIC)
            .build();
    }

    @Bean
    public ProducerFactory<String, UserEvent> userProducerFactory(){
        Map<String, Object> props = new HashMap<>();
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
}
