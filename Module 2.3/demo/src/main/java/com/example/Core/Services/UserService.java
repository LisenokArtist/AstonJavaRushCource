package com.example.Core.Services;

import java.util.List;

import com.example.Core.Dao.User.UserDao;
import com.example.Core.Entities.User.User;

public class UserService {
    private final UserDao userDao;

    public UserService(){
        userDao = new UserDao();
    }

    public UserService(UserDao dao){
        userDao = dao;
    }

    /**
     * Поиск пользователя по id
     * @param id Идентификатор пользователя
     * @return Object если запись найдена или null
     */
    public User findUserById(int id){
        return userDao.findById(id);
    }

    /**
     * Сохранение пользователя
     * @param name имя
     * @param age возраст
     * @param email электронный адрес
     */
    public void saveUser(User user){
        userDao.save(user);
    }

    /**
     * Сохранение пользователя
     * @param name имя
     * @param age возраст
     * @param email электронный адрес
     */
    public void saveUser(String name, int age, String email){
        userDao.save(new User(name, age, email));
    }

    /**
     * Обновляет имя пользователя
     * @param id идентификатор пользователя
     * @param name новое имя пользователя
     * @return true если обновление прошло успешно
     */
    public boolean updateUser(int id, String name){
        return userDao.update(id, name);
    }

    /**
     * Удаление пользователя по id
     * @param id Идентификатор
     */
    public boolean deleteUserById(int id){
        int result = userDao.delete(id);
        return result > 0;
    }

    /**
     * Выводит список пользователей
     * @param limit кол-во запрашиваемых записей
     * @return Список пользователей
     */
    public List<User> users(int limit){
        return userDao.list(limit);
    }

    /**
     * Выводит список пользователей не более 10 записей
     * @return Список пользователей
     */
    public List<User> users(){
        return userDao.list();
    }
}
