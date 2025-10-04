package com.example.demo.Core.Services.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Core.DataModels.Entities.User.User;
import com.example.demo.Core.DataModels.Models.User.UserShort;
import com.example.demo.Core.Repositories.User.UserRepository;
import com.example.demo.Core.Services.EmailService;
import com.example.demo.Core.Services.Notification.User.UserEventSender;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final UserEventSender sender;
    @Autowired
    private final EmailService emailService;

    public UserService(UserRepository repository, UserEventSender sender, EmailService emailService){
        this.repository = repository;
        this.sender = sender;
        this.emailService = emailService;
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

    public void sendEmail(String to, String subject, String text){
        emailService.sendEmail(to, subject, text);
    }
}
