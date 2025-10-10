// package com.example;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.example.controllers.user.UserController;
// import com.example.datamodels.entities.user.User;
// import com.example.datamodels.models.user.UserCreate;
// import com.example.datamodels.models.user.UserUpdate;

// @SpringBootTest
// public class UserControllerTest {
//     @Autowired
//     private UserController controller;

//     @Test
//     void shouldDoNothing(){
//         assertTrue(true);
//     }

//     @Test
//     void shouldGetUsers(){
//         var result = controller.getUsers();
//         assertTrue(!result.getBody().getContent().isEmpty());
//     }
    
//     @Test
//     void shouldGetUserById(){
//         var firstUser = controller.getUsers().getBody().getContent().get(0);
//         var result = controller.getUserById(firstUser.getId());
//         assertEquals(firstUser.getId(), result.getBody().getContent().getId());
//     }

//     @Test
//     void shouldCreateUser(){
//         var user = createUsers(1).get(0);
//         var result = controller.createUser(new UserCreate(user));
//         assertEquals(user.getName(), result.getBody().getContent().getName());
//     }

//     @Test
//     void shouldUpdateUser(){
//         var firstUser = controller.getUsers().getBody().getContent().get(0);
//         var newName = "New user name";
//         var result = controller.updateUser(new UserUpdate(firstUser.getId(), newName));
//         assertEquals(result.getBody().getContent().getName(), newName);
//     }

//     @Test
//     void shouldDeleteUserById(){
//         var firstUser = controller.getUsers().getBody().getContent().get(0);
//         var result = controller.deleteUserById(firstUser.getId());
//         assertEquals(firstUser.getId(), result.getBody().getContent().getId());
//         assertEquals(firstUser.getName(), result.getBody().getContent().getName());
//     }

//     @Test
//     void shouldCount(){
//         var result = controller.count();
//         assertTrue(result.getBody() > 0);
//     }

//     private static List<User> createUsers(int count){
//         List<User> users = new ArrayList<>();
//         int index = 0;
//         while (index < count){
//             User user = new User(
//                 String.format("User%d", index),
//                 19 + index, 
//                 String.format("user%d@mail.com", index));
//             users.add(user);
//             index ++;
//         }
//         return users;
//     }
// }