package com.example.services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datamodels.entities.user.User;
import com.example.repositories.user.UserRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
    

    public UserService(UserRepository repository){
        this.repository = repository;
    }


    @CircuitBreaker(name="countCircuitBreaker", fallbackMethod="onUsersFallback")
    public List<User> findAll(){
        List<User> result = repository.findAll();
        
        if (Math.random() < 0.3) { // 30% chance of failure
            throw new RuntimeException("External API call failed!");
        }
        
        return result;
    }

    public String onUsersFallback(Throwable t){
        System.err.println("Fallback triggered: " + t.getMessage());
        return "onUsersFallback";
    }

    public long count(){
        return repository.count();
    }

    public List<User> saveAll(List<User> entities){
        return repository.saveAll(entities);
    }
}