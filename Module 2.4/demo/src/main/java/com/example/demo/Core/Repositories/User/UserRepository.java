package com.example.demo.Core.Repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Core.Entities.User.User;

import jakarta.transaction.Transactional;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT e FROM User e ORDER BY e.id ASC FETCH FIRST 1 ROW ONLY")
    public User findFirst(); //"Голый" метод выдаст ошибку No property 'findFirst' found for type 'User', а длинные названия методов тут не нужны

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :newName WHERE u.id = :userId")
    public int updateUserName(@Param("newName") String newName, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :ID")
    public int deleteById(@Param("ID") int id); //Есть стандартный метод, но нам нужен результат затронутых строк к удалению
}
