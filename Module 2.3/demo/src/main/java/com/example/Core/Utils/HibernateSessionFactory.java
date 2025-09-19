package com.example.Core.Utils;

import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.example.Core.Entities.User.User;



public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory(){}

    public static SessionFactory getSessionFactory(){
        return getSessionFactory(new Configuration().configure());
    }

    public static SessionFactory getSessionFactory(Configuration configuration){
        if (sessionFactory == null){
            try {
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (HibernateException e) {
                System.out.println(e.toString());
            }
        }
        
        return sessionFactory;
    }
}
