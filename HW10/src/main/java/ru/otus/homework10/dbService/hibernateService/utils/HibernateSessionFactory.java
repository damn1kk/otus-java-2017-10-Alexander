package ru.otus.homework10.dbService.hibernateService.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Closeable;
import java.io.IOException;

public class HibernateSessionFactory implements Closeable {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch(Throwable ex){
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }
}
