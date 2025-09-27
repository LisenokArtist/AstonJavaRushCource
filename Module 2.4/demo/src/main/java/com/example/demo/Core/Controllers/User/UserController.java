package com.example.demo.Core.Controllers.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Core.Entities.User.User;
import com.example.demo.Core.Entities.User.UserCreate;
import com.example.demo.Core.Entities.User.UserShort;
import com.example.demo.Core.Entities.User.UserUpdate;
import com.example.demo.Core.Services.User.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private final UserService service;

    
    public UserController(UserService service){
        this.service = service;
    }

    
    @GetMapping("/users")
    public List<UserShort> findAll(){
        List<UserShort> result = service.findAll();
        return result;
    }

    @GetMapping("/user/{id}")
    public UserShort getUserById(@PathVariable(name="id") int id){
        UserShort result = service.findById(id);
        return result;
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserShort> createUser(@RequestBody UserCreate user){
        UserShort result = service.save(new User(user.getName(), user.getAge(), user.getEmail()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/updateuser")
    public int updateUser(@RequestBody UserUpdate user){
        int result = service.updateName(user.getName(), user.getId());
        return result;
    }

    @DeleteMapping("/deleteuser/{id}")
    public int deleteUserById(@RequestBody int id){
        int result = service.deleteById(id);
        return result;
    }
}