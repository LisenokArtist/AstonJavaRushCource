package com.example.demo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Core.Entities.User.User;
import com.example.demo.Core.Repositories.User.UserRepository;
import com.example.demo.Core.Shared.UnitTestUtils;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;



    @BeforeEach
    @SuppressWarnings("unused")
    void onEveryStartedTest(){
        UnitTestUtils.fillDataBaseIfEmpty(repository);
    }



    @Test 
    void shouldFindFirstRecordTest(){
        User result = repository.findFirst();
        assertTrue(result != null);
    }

    @Test
    public void shouldFindAllRecordsTest(){
        List<User> result = repository.findAll();
        assertTrue(!result.isEmpty());
    }

    @Test
    public void shouldFindRecordByIdTest(){
        int id = repository.findFirst().getId();
        Optional<User> result = repository.findById(id);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void shouldAddRecordTest(){
        User result = repository.save(UnitTestUtils.createUsers(1).get(0));
        assertTrue(!repository.findById(result.getId()).isEmpty());
    }

    @Test
    public void shouldUpdateNameRecordTest(){
        int id = repository.findFirst().getId();
        int result = repository.updateUserName("NewUserName", id);
        assertTrue(result > 0);
        assertTrue(!repository.findById(id).isEmpty());
    }

    @Test
    public void shouldDeleteRecordByIdTest(){
        int id = repository.findFirst().getId();
        int result = repository.deleteById(id);
        assertTrue(result > 0);
        assertTrue(repository.findById(id).isEmpty());
    }
}
