package com.example.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.datamodels.entities.user.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT e FROM User e ORDER BY e.id ASC FETCH FIRST 1 ROW ONLY")
    public User findFirst();

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :newName WHERE u.id = :userId")
    public int updateUserName(@Param("newName") String newName, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :ID")
    public int deleteById(@Param("ID") int id);

    @Transactional
    @Modifying
    public default Optional<User> findAndDeleteById(@Param("ID") int id){
        Optional<User> entity = this.findById(id);
        
        if (entity.isPresent()) {
            this.deleteById(entity.get().getId());
        }
            
        return entity;
    }
}
