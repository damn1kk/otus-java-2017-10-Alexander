package ru.otus.homework15.database.dbService.hibernateService.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.logging.Logger;

public class HibernateSessionFactory{
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        Configuration cfg = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(ru.otus.homework15.database.dataSet.UserDataSet.class)
                .addAnnotatedClass(ru.otus.homework15.database.dataSet.PhoneDataSet.class)
                .addAnnotatedClass(ru.otus.homework15.database.dataSet.AddressDataSet.class)
                .addAnnotatedClass(ru.otus.homework15.database.dataSet.PasswordDataSet.class)
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:/h2database/userdatasetDB");
        return cfg.buildSessionFactory();
    }


    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void closeSessionFactory() throws IOException {
        sessionFactory.close();
    }
}
