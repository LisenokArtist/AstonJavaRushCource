package com.example.demo.Core.Controllers.User;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Core.Services.User.UserService;

// @RestController
// @RequestMapping("/api")
public class UserController {
    @Autowired
    private final UserService service;

    
    public UserController(UserService service){
        this.service = service;
    }
}