package com.example.demo.Core.DataModels.Entities.User;

import com.example.demo.Core.DataModels.Entities.EntityBase;

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


    /**
     * Задает значение имени
     * @param name значение имени
     */
    public void setName(String name){
        this.name = name;
        this.updateUpdatedAt();
    }

    /**
     * Задает значение возраста
     * @param age значение возраста
     */
    public void setAge(int age){
        this.age = age;
        this.updateUpdatedAt();
    }

    /**
     * Задает значение почтового адреса
     * @param email значение почтового адреса
     */
    public void setEmail(String email){
        this.email = email;
        this.updateUpdatedAt();
    }

    @Override
    public String toString(){
        return String.format("[%d] %s %d %s", this.getId(), this.getName(), this.getAge(), this.getEmail());
    }
}

