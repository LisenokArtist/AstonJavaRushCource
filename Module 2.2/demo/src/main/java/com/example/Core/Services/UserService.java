package com.example.Core.Services;

import java.util.List;

import com.example.Core.Dao.User.UserDao;
import com.example.Core.Entities.User.User;

public class UserService {
    private final UserDao userDao = new UserDao();

    public UserService(){}

    /**
     * Поиск пользователя по id
     * @param id Идентификатор пользователя
     * @return Object если запись найдена или null
     */
    public User findUser(int id){
        return userDao.findById(id);
    }

    /**
     * Сохранение пользователя
     * @param obj Пользователь
     */
    public void saveUser(User obj){
        userDao.save(obj);
    }

    /**
     * Обновление пользователя
     * @param obj Пользователь
     */
    public void updateUser(User obj){
        userDao.update(obj);
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
     * Удаление пользователя
     * @param obj Пользователь
     */
    public void deleteUser(User obj){
        userDao.delete(obj);
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
        return userDao.users(limit);
    }

    /**
     * Выводит список пользователей не более 10 записей
     * @return Список пользователей
     */
    public List<User> users(){
        return userDao.users();
    }
}
