package com.example.demo.Core.Services.User;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Core.Entities.User.User;
import com.example.demo.Core.Entities.User.UserShort;
import com.example.demo.Core.Repositories.User.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository repository;


    public UserService(UserRepository repository){
        this.repository = repository;
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
        var result = repository.findById(id);
        if (result.isPresent()){
            return result.map(x -> new UserShort(x)).get();
        } else {
            return null;
        }
    }

    public UserShort save(User user){
        User result = repository.save(user);
        return new UserShort(result);
    }
    
    public int updateName(String name, int id){
        int result = repository.updateUserName(name, id);
        return result;
    }

    public int deleteById(int id){
        int result = repository.deleteById(id);
        return result;
    }

    public long count(){
        return repository.count();
    }

    public void saveAll(List<User> users){
        repository.saveAll(users);
    }
}
