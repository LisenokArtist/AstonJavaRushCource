package com.example.Core.Entities.User;

import com.example.Core.Entities.Base.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "users")
public class User extends EntityBase {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    public User(){}

    public User(String name, int age, String email){
        super();
        this.name = name;
        this.age = age;
        this.email = email;
    }

    /**
     * Получает значение имени
     * @return String значение имени
     */
    public String getName(){
        return this.name;
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
     * Получает значение возраста
     * @return int значение возраста
     */
    public int getAge(){
        return this.age;
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
     * Получает значение почтового адреса
     * @return String значение почтового адреса
     */
    public String getEmail(){
        return this.email;
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
        return String.format("[%d] %s %d %s", this.getId(), this.name, this.age, this.email);
    }
}
