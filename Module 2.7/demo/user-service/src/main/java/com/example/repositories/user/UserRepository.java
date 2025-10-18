package com.example.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.datamodels.entities.user.User;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT e FROM User e ORDER BY e.id ASC FETCH FIRST 1 ROW ONLY")
    public User findFirst();

    @Transactional
    @Modifying
    public default Optional<User> update(
        int id, 
        @Nullable String name, 
        @Nullable Integer age, 
        @Nullable String email){
        Optional<User> entity = this.findById(id);

        if (entity.isPresent()) {
            if (name != null) entity.get().setName(name);
            if (age != null) entity.get().setAge(age);
            if (email != null) entity.get().setEmail(email);
            return Optional.ofNullable(this.save(entity.get()));
        }
        return null;
    }

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
