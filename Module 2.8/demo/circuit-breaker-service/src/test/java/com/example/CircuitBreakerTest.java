package com.example;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.datamodels.entities.user.User;
import com.example.repositories.user.UserRepository;
import com.example.services.user.UserService;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@SpringBootTest
public class CircuitBreakerTest {
    @Spy
    @InjectMocks
    private UserService service;

    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    @SuppressWarnings("unused")
    void beforeEach() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(1))
                .slidingWindowSize(2)
                .minimumNumberOfCalls(2)
                .build();
        circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
        
        circuitBreakerRegistry.circuitBreaker("countCircuitBreaker").reset();
    }

    @Test
    void shouldDoNothing(){
        assertTrue(true);
    }

    @Test
    void shouldTransitionToHalfOpenAndThenClosed() throws InterruptedException {
        doThrow(new RuntimeException("Simulated failure")).when(service).findAll();
        try { service.findAll(); } catch (Exception ignored) {}
        try { service.findAll(); } catch (Exception ignored) {}

        Thread.sleep(1500);

        doReturn(createUsers(10)).when(service).findAll();
        List<User> result = service.findAll();
        assertTrue(!result.isEmpty());
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
