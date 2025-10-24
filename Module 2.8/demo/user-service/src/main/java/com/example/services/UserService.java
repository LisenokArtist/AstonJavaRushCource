package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserCreate;
import com.example.datamodels.models.user.UserShort;
import com.example.repositories.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private static final String USERNOTFOUND = "User not found";
    
    @Autowired
    private final UserRepository repository;
    
    @Autowired
    private final UserEventSender sender;

    public UserService(UserRepository repository, UserEventSender sender){
        this.repository = repository;
        this.sender = sender;
    }

    public UserShort findFirst(){
        User result = repository.findFirst();
        return new UserShort(result);
    }

    public List<UserShort> findAll(){
        List<UserShort> result = repository
                                .findAll()
                                .stream()
                                .map(x -> new UserShort(x))
                                .collect(Collectors.toList());
        return result;
    }

    public UserShort findById(int id){
        Optional<User> result = repository.findById(id);
        User user = result.orElseThrow(() -> new EntityNotFoundException(USERNOTFOUND));
        return new UserShort(user);
    }

    public UserShort save(UserCreate userCreate){
        User result = repository.save(new User(userCreate.getName(), userCreate.getAge(), userCreate.getEmail()));
        sender.sendEventCreate(result);
        return new UserShort(result);
    }

    public List<UserShort> saveAll(List<UserCreate> users){
        var usersCreated = users.stream()
                                .map(x -> new User(x.getName(), x.getAge(), x.getEmail()))
                                .collect(Collectors.toList());
        List<User> result = repository.saveAll(usersCreated);
        return result.stream().map(x -> new UserShort(x)).collect(Collectors.toList());
    }

    public UserShort update(User user){
        return update(
            user.getId(), 
            user.getName(),
            user.getAge(),
            user.getEmail());
    }

    public UserShort update(
        int id,
        String name,
        Integer age,
        String email
    ){
        Optional<User> result = repository.update(id, name, age, email);
        User user = result.orElseThrow(() -> new EntityNotFoundException(USERNOTFOUND));
        return new UserShort(user);
    }

    public UserShort delete(int id){
        Optional<User> result = repository.findAndDeleteById(id);
        User user = result.orElseThrow(() -> new EntityNotFoundException(USERNOTFOUND));
        sender.sendEventDelete(result.get());
        return new UserShort(user);
    }
    
    public long count(){
        return repository.count();
    }
}