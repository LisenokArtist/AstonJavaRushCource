package com.example.demo.Core.Shared;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Core.Entities.User.User;
import com.example.demo.Core.Repositories.User.UserRepository;
import com.example.demo.Core.Services.User.UserService;

public class UnitTestUtils {
    public static void fillDataBaseIfEmpty(UserRepository repository){
        if (repository.count() <= 0){
            List<User> users = createUsers(10);
            repository.saveAll(users);
        }
    }

    public static void fillDataBaseIfEmpty(UserService service){
        if (service.count() <= 0){
            List<User> users = createUsers(10);
            service.saveAll(users);
        }
    }

	public static List<User> createUsers(int count){
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
