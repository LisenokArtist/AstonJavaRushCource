package com.example;


import java.util.List;

import org.hibernate.cfg.Configuration;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.example.Core.Entities.User.User;
import com.example.Core.Services.UserService;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void listUsersTest(){
        UserService userService = new UserService();
        List<User> result = userService.users();
        System.out.println(result);
    }

    @Test
    public void configure(){
        Configuration conf = new Configuration().configure();
        return;
    }
}
