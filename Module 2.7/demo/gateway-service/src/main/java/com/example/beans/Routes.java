package com.example.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {
    @Bean
    public RouteLocator WebRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service-route", r -> r
                .path("/users/**")
                .filters(f -> f
                    .rewritePath("users/(?<segment>.*)", "/api/users/${segment}")
                    .addRequestHeader("X-Gateway-Request", "true"))
                .uri("http://localhost:8082"))
            .route("user-notification-service-route", r -> r
                .path("/email/**")
                .filters(f -> f
                    .rewritePath("email/(?<segment>.*)", "/api/email/${segment}")
                    .addRequestHeader("X-Gateway-Request", "true"))
                .uri("http://localhost:8083"))
            .build();
    }
}
