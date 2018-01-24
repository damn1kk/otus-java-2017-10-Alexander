package ru.otus.homework12.database.dbService.hibernateService;

import ru.otus.homework12.database.dataSet.PasswordDataSet;
import ru.otus.homework12.database.dbService.hibernateService.utils.HibernateSessionFactory;


public class HibernatePasswordDBService extends HibernateDBService<PasswordDataSet, String> {
    public HibernatePasswordDBService(){
        super(PasswordDataSet.class);
        this.sessionFactory = HibernateSessionFactory.getSessionFactoryToPassword();
    }

}
