package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DataModels.Entities.User.User;
import com.example.DataModels.Models.User.UserShort;
import com.example.Repositories.User.UserRepository;

@Service
public class UserService {
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

    public void saveAll(List<User> users){
        repository.saveAll(users);
    }

    public long count(){
        return repository.count();
    }

    public UserShort save(User user){
        User newUser = repository.save(user);
        sender.sendEventCreate(newUser);
        return new UserShort(newUser);
    }

    public UserShort delete(int id){
        Optional<User> deletedUser = repository.findAndDeleteById(id);
        if (deletedUser.isPresent()){
            sender.sendEventDelete(deletedUser.get());
            return new UserShort(deletedUser.get());
        } else {
            return null;
        }
    }
}