package com.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.datamodels.entities.user.User;
import com.example.repositories.user.UserRepository;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    @SuppressWarnings("unused")
    void beforeEach(){
        fillDataBaseIfEmpty(repository);
    }



    @Test
    void shouldDoNothing(){
        assertTrue(true);
    }

    @Test
    void shoulFindFirst(){
        var result = repository.findFirst();
        assertNotNull(result);
    }

    @Test
    void shouldUpdateNameOnly(){
        var user = repository.findFirst();
        var newName = "new user name";
        var updatedUser = repository.update(user.getId(), newName, null, null);
        assertTrue(updatedUser.isPresent());
        assertEquals(user.getId(), updatedUser.get().getId());
        assertEquals(newName, updatedUser.get().getName());
        assertEquals(user.getAge(), updatedUser.get().getAge());
        assertEquals(user.getEmail(), updatedUser.get().getEmail());
    }

    @Test
    void shouldFindAndDeleteById(){
        var user = repository.findFirst();
        var deletedUser = repository.findAndDeleteById(user.getId());
        assertTrue(deletedUser.isPresent());
        assertEquals(user.getId(), deletedUser.get().getId());
        assertEquals(user.getName(), deletedUser.get().getName());
        assertEquals(user.getAge(), deletedUser.get().getAge());
        assertEquals(user.getEmail(), deletedUser.get().getEmail());
    }
    
    

    private static void fillDataBaseIfEmpty(UserRepository repository){
        if (repository.count() <= 0){
            List<User> users = createUsers(10);
            repository.saveAll(users);
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
