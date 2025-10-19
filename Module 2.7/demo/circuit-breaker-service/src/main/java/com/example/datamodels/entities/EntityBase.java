package com.example.datamodels.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="createdat")
    private final LocalDateTime createdAt;

    @Column(name="updatedat")
    private LocalDateTime updatedAt;

    public EntityBase(){
        this.createdAt = LocalDateTime.now();
    }
}