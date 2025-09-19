package com.example.Core.Dao.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.example.Core.Dao.Base.Dao;
import com.example.Core.Entities.User.User;
import com.example.Core.Utils.HibernateSessionFactory;

public class UserDao implements Dao<User>{
    private SessionFactory sessionFactory;

    public UserDao(){
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    public UserDao(SessionFactory factory){
        sessionFactory = factory;
    }

    @Override
    public User findById(int id) {
        User result;
        try (Session session = sessionFactory.openSession()) {
            result = session.find(User.class, id);
        }
        return result;
    }

    @Override
    public void save(User obj) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            session.persist(obj);
            transaction.commit();
        }
    }

    @Override
    public void update(User obj) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            session.merge(obj);
            transaction.commit();
        }
    }

    /**
     * Обновляет имя пользователя по его идентификатору
     * @param id идентификатор пользователя
     * @param name новое имя пользователя
     * @return true если запись обновлена
     */
    public boolean  update(int id, String name){
        int result;
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User SET name = :Name WHERE id = :ID");
            query.setParameter("ID", id);
            query.setParameter("Name", name);
            result = query.executeUpdate();
            transaction.commit();
        }
        return result > 0;
    }

    @Override
    public void delete(User obj) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            session.remove(obj);
            transaction.commit();
        }
    }

    /**
     * Удаляет пользователя по его id
     * @param id идентификатор пользователя
     * @return int значение удаленных совпадений
     */
    public int delete(int id){
        int result;
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :ID");
            query.setParameter("ID", id);
            result = query.executeUpdate();
            transaction.commit();
        }
        return result;
    }

    /**
     * Выводит список пользователей
     * @param limit кол-во запрашиваемых записей
     * @return Список пользователей
     */
    public List<User> list(int limit){
        List<User> result;
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User").setMaxResults(limit);
            result = query.list();
            transaction.commit();
        }
        return result;
    }

    /**
     * Выводит список пользователей не более 10 записей
     * @return Список пользователей
     */
    public List<User> list(){
        return list(10);
    }
}
