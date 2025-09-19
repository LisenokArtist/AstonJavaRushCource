package com.example.Core.Dao.Base;

public interface Dao<T> {
    /**
     * Поиск сущности по id
     * @param id Идентификатор сущности
     * @return Object если запись найдена или null
     */
    T findById(int id);

    /**
     * Сохранение сущности
     * @param obj Сущность
     */
    void save(T obj);

    /**
     * Обновление сущности
     * @param obj Сущность
     */
    void update(T obj);

    /**
     * Удаление записи
     * @param obj Сущность
     */
    void delete(T obj);
}
