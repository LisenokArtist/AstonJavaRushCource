package com.example.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.demo.Core.Controllers.User.UserController;
import com.example.demo.Core.Entities.User.UserCreate;
import com.example.demo.Core.Entities.User.UserShort;
import com.example.demo.Core.Entities.User.UserUpdate;
import com.example.demo.Core.Services.User.UserService;

@SpringBootTest
public class UserControllerTests {
    @Autowired
    private UserController controller;

    @Autowired
    private UserService service;
    
    @Test
    void shouldListUsersTest(){
        List<UserShort> result = controller.findAll();
        assertTrue(!result.isEmpty());
    }

    @Test
    void shouldFindUserByIdTest(){
        UserShort userShort = service.findFirst();
        UserShort result = controller.getUserById(userShort.getId());
        assertTrue(result != null);
    }

    @Test
    void shouldCreateNewUserTest(){
        UserCreate newUser = new UserCreate("New user created", 20, "newusercreated@mail.com");
        ResponseEntity<UserShort> result = controller.createUser(newUser);
        assertTrue(!result.getStatusCode().isError());
    }

    @Test
    void shouldUpdateUserTest(){
        UserShort userShort = service.findFirst();
        UserUpdate updateUser = new UserUpdate(userShort.getId(), "Updated user name");
        int result = controller.updateUser(updateUser);
        assertTrue(result > 0);
    }

    @Test
    void shouldRemoveUserTest(){
        UserShort userShort = service.findFirst();
        int result = controller.deleteUserById(userShort.getId());
        assertTrue(result > 0);
    }
}
