package com.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.Core.Dao.User.UserDao;
import com.example.Core.Entities.User.User;
import com.example.Core.Utils.HibernateSessionFactory;

public class ContainersTest {

    /**
     * Т.к. в тестируемой DAO не реализованы методы для 
     * очистки таблиц БД, реализуем отдельное решение
     * в пределах этого класса для тестов.
     */
    /** Наш контейнер докера */
    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
    
    private UserDao userDao;

    @Before
    public void OnStartupTest() throws SQLException{
        userDao = new UserDao(HibernateSessionFactory.getSessionFactory(getConfigurationFromContainer(postgres)));

        tryClearDataBase();
        fillDataBaseWithEntity();
    }



    @Test
    public void shouldLocalyCreateUserCollection(){
        int count = 10;
        List<User> users = createUsers(count);
        assertTrue(count == users.size());
    }

    @Test
    public void shouldClearTable() throws SQLException{
        tryClearDataBase();
        assertTrue(userDao.list().isEmpty());
    }

    @Test
    public void shouldFetchUserFromDB(){
        assertTrue(!userDao.list().isEmpty());
    }

    @Test
    public void shouldFindUserById(){
        User user = userDao.list(1).get(0);

        assertTrue(userDao.findById(user.getId()) != null);
    }

    @Test
    public void shouldUpdateUserName(){
        List<User> users = userDao.list(10);
        User randomUser = users.get(new Random().nextInt(users.size()));
        String username = "New User Name";
        randomUser.setName(username);
        userDao.update(randomUser);
        
        assertTrue(userDao.findById(randomUser.getId()).getName().equals(username));
    }

    @Test
    public void shouldDeleteUser(){
        User user = userDao.list(1).get(0);

        assertTrue(userDao.delete(user.getId()) > 0);
    }



    /**
     * Создает несколько экземпляров объекта типа User
     * @param count кол-во экземпляров
     * @return список объектов типа User
     */
    private List<User> createUsers(int count){
        List<User> users = new ArrayList<User>();
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

    /**
     * Чистит таблицу от мусора
     * @throws SQLException
     */
    private void tryClearDataBase() throws SQLException{
        try (Connection connection = DriverManager.getConnection(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword())){
            if (tableIsExist(connection, "users", "public")) {
                truncateTable(connection, "users");
            }
        }
    }

    /**
     * Проверка существует ли таблица
     * @param connection Класс отвечающий за подключение к базе
     * @param tableName Имя таблицы
     * @param schemaName Название схемы
     * @return True если таблица существует
     * @throws SQLException
     */
    private boolean tableIsExist(Connection connection, String tableName, String schemaName) throws SQLException{
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = ? AND table_name = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, schemaName);
            statement.setString(2, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        }
        return false;
    }

    /**
     * Очищает таблицу
     * @param connection Класс отвечающий за подключение к базе
     * @param tableName Имя таблицы
     * @throws SQLException
     */
    private void truncateTable(Connection connection, String tableName) throws SQLException{
        String sql = "TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    /**
     * Заполняет базу тестовой записью
     */
    private void fillDataBaseWithEntity(){
        User user = createUsers(1).get(0);
        userDao.save(user);
    }

    /** Метод получения класса конфигураций контейнера для тестируемой DAO */
    private Configuration getConfigurationFromContainer(PostgreSQLContainer<?> container){
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", container.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", container.getUsername());
        configuration.setProperty("hibernate.connection.password", container.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return configuration;
    }
}
