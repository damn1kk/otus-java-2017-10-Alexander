package ru.otus.homework13.database.dbService.hibernateService.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class HibernateSessionFactory{
    private static final SessionFactory sessionFactoryToPassword = buildSessionFactoryToPassword();
    private static final SessionFactory sessionFactoryToUserDataSet = buildSessionFactoryToUserDataSet();

    private static SessionFactory buildSessionFactoryToUserDataSet(){
        Configuration cfg = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(ru.otus.homework13.database.dataSet.UserDataSet.class)
                .addAnnotatedClass(ru.otus.homework13.database.dataSet.PhoneDataSet.class)
                .addAnnotatedClass(ru.otus.homework13.database.dataSet.AddressDataSet.class)
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:/h2database/userdatasetDB");
        return cfg.buildSessionFactory();
    }

    private static SessionFactory buildSessionFactoryToPassword(){
        Configuration cfg = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(ru.otus.homework13.database.dataSet.PasswordDataSet.class)
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:/h2database/passwordDB");
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
