package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.datamodels.entities.user.User;
import com.example.datamodels.models.user.UserCreate;
import com.example.services.UserService;

@SpringBootTest(classes = UserApplicationService.class)
public class UserServiceTest {
    @Autowired
    private UserService service;

    @BeforeEach
    @SuppressWarnings("unused")
    void beforeEach(){
        fillDataBaseIfEmpty(service);
    }

    

    @Test
    void shouldDoNothing(){
        assertTrue(true);
    }

    @Test
    void shouldFindFirst(){
        var result = service.findFirst();
        assertNotNull(result);
    }

    @Test
    void shouldFindAll(){
        var result = service.findAll();
        assertTrue(!result.isEmpty());
    }

    @Test
    void shouldFindById(){
        var query = service.findFirst();
        var result = service.findById(query.getId());
        assertEquals(query.getId(), result.getId());
    }

    @Test
    void shouldSave(){
        var user = createUsers(1).get(0);
        var result = service.save(new UserCreate(user));
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    void shouldSaveAll(){
        var users = createUsers(3)
                    .stream()
                    .map(x -> new UserCreate(x.getName(), x.getAge(), x.getEmail()))
                    .collect(Collectors.toList());
        var result = service.saveAll(users);
        assertTrue(!result.isEmpty());
        assertEquals(result.size(), users.size());
    }

    @Test
    void shouldUpdateNameOnly(){
        var user = service.findFirst();
        var newName = "new user name";
        var updatedUser = service.update(user.getId(), newName, null, null);
        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(newName, updatedUser.getName());
    }

    @Test
    void shouldDeleteById(){
        var user = service.findFirst();
        var deletedUser = service.delete(user.getId());
        assertNotNull(deletedUser);
        assertEquals(user.getId(), deletedUser.getId());
        assertEquals(user.getName(), deletedUser.getName());
    }



    private static void fillDataBaseIfEmpty(UserService service){
        if (service.count() <= 0){
            var users = createUsers(10)
                    .stream()
                    .map(x -> new UserCreate(x.getName(), x.getAge(), x.getEmail()))
                    .collect(Collectors.toList());
            service.saveAll(users);
        }
    }

	private static List<User> createUsers(int count){
        List<User> users = new ArrayList<>();
        int index = 0;
        while (index < count){
            User user = new User(
                String.format("User%d", index),
                19 + index, 
                String.format("user%d@mail.com", index));
            users.add(user);
            index ++;
        }
        return users;
    }
}
