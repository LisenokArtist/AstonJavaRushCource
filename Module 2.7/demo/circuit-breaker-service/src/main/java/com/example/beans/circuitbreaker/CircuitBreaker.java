package com.example.beans.circuitbreaker;

// import java.time.Duration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
// import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

// @Configuration
// public class CircuitBreaker {
//     @Bean
//     public io.github.resilience4j.circuitbreaker.CircuitBreaker countCircuitBreaker(){
//         CircuitBreakerConfig config = CircuitBreakerConfig.custom()
//             .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//             .slidingWindowSize(10)
//             .slowCallRateThreshold(65.0f)
//             .slowCallDurationThreshold(Duration.ofSeconds(3))
//             .build();

//         CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);

//         io.github.resilience4j.circuitbreaker.CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("countCircuitBreaker");

//         return cb;
//     }

//     @Bean(name="timeCircuitBreaker")
//     public io.github.resilience4j.circuitbreaker.CircuitBreaker timeCircuitBreaker(){
//         CircuitBreakerConfig config = CircuitBreakerConfig.custom()
//                 .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
//                 .minimumNumberOfCalls(3)
//                 .slidingWindowSize(10)
//                 .failureRateThreshold(70.0f)
//                 .build();

//         CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);

//         io.github.resilience4j.circuitbreaker.CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("timeCircuitBreaker");
//         return cb;
//     }
// }
