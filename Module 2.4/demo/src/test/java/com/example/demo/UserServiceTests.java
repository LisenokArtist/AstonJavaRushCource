package com.example.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Core.Entities.User.UserShort;
import com.example.demo.Core.Services.User.UserService;
import com.example.demo.Core.Shared.UnitTestUtils;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService service;



    @BeforeEach
    @SuppressWarnings("unused")
    void onEveryStartedTest(){
        UnitTestUtils.fillDataBaseIfEmpty(service);
    }



    @Test 
    void shouldFindFirstRecordTest(){
        UserShort result = service.findFirst();
        assertTrue(result != null);
    }

    @Test
    public void shouldFindAllRecordsTest(){
        List<UserShort> result = service.findAll();
        assertTrue(!result.isEmpty());
    }

    @Test
    public void shouldFindRecordByIdTest(){
        int id = service.findFirst().getId();
        UserShort result = service.findById(id);
        assertTrue(result != null);
    }

    @Test
    public void shouldAddRecordTest(){
        var result = service.save(UnitTestUtils.createUsers(1).get(0));
        assertTrue(service.findById(result.getId()) != null);
    }

    @Test
    public void shouldUpdateNameRecordTest(){
        int id = service.findFirst().getId();
        int result = service.updateName("NewUserName", id);
        assertTrue(result > 0);
        assertTrue(service.findById(id) != null);
    }

    @Test
    public void shouldDeleteRecordByIdTest(){
        int id = service.findFirst().getId();
        int result = service.deleteById(id);
        assertTrue(result > 0);
        assertTrue(service.findById(id) == null);
    }

    @Test
    public void shouldCountRecords(){
        long count = service.count();
        assertTrue(count > 0);
    }
}
