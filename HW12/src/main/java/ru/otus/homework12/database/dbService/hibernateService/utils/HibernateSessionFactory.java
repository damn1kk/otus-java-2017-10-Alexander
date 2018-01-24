package ru.otus.homework12.database.dbService.hibernateService.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.io.IOException;

public class HibernateSessionFactory{
    private static final SessionFactory sessionFactoryToPassword = buildSessionFactoryToPassword();
    private static final SessionFactory sessionFactoryToUserDataSet = buildSessionFactoryToUserDataSet();

    private static SessionFactory buildSessionFactoryToUserDataSet(){
        /*final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch(Throwable ex){
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }*/
        Configuration cfg = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(ru.otus.homework12.database.dataSet.UserDataSet.class)
                .addAnnotatedClass(ru.otus.homework12.database.dataSet.PhoneDataSet.class)
                .addAnnotatedClass(ru.otus.homework12.database.dataSet.AddressDataSet.class)
                .setProperty("hibernate.connection.url", "jdbc:h2:./h2database/userdatasetDB");
        return cfg.buildSessionFactory();
    }

    private static SessionFactory buildSessionFactoryToPassword(){
        Configuration cfg = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(ru.otus.homework12.database.dataSet.PasswordDataSet.class)
                .setProperty("hibernate.connection.url", "jdbc:h2:./h2database/passwordDB");
        return cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactoryToPassword(){
        return sessionFactoryToPassword;
    }

    public static SessionFactory getSessionFactoryToUserDataSet(){
        return sessionFactoryToUserDataSet;
    }

    public static void closeToUserDataSet() throws IOException {
        sessionFactoryToUserDataSet.close();
    }

    public static void closeToPassword() throws IOException{
        sessionFactoryToPassword.close();
    }
}
