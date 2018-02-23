package ru.otus.homework13.database.dbService.hibernateService;

import ru.otus.homework13.database.dataSet.PasswordDataSet;
import ru.otus.homework13.database.dbService.hibernateService.utils.HibernateSessionFactory;


public class HibernatePasswordDBService extends HibernateDBService<PasswordDataSet, String> {
    public HibernatePasswordDBService(){
        super(PasswordDataSet.class);
        this.sessionFactory = HibernateSessionFactory.getSessionFactoryToPassword();
    }

}
