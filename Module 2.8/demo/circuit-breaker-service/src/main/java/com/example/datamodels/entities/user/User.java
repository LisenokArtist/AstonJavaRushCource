package com.example.datamodels.entities.user;

import com.example.datamodels.entities.EntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User extends EntityBase {
    private String name;

    private int age;

    private String email;

    public User(){}

    public User(String name, int age, String email){
        super();
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString(){
        return String.format("[%d] %s %d %s", this.getId(), this.getName(), this.getAge(), this.getEmail());
    }
}

