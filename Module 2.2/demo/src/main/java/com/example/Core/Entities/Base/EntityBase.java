package com.example.Core.Entities.Base;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public EntityBase(){
        this.createdAt = LocalDateTime.now();
        updateUpdatedAt();
    }

    /**
     * Ключ значения
     */
    public final int getId(){
        return this.id;
    }

    /**
     * Дата создания записи
     */
    public final LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    /**
     * Дата изменения записи
     */
    public final LocalDateTime getUpdatedAt(){
        return this.updatedAt;
    }

    /**
     * Обновляет дату создания
     */
    protected final void updateUpdatedAt(){
        this.updatedAt = this.createdAt;
    }
}
