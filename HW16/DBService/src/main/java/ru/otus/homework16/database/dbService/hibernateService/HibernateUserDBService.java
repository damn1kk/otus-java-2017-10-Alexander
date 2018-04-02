package ru.otus.homework16.database.dbService.hibernateService;

import ru.otus.homework16.database.dataSet.UserDataSet;
import ru.otus.homework16.database.dbService.hibernateService.utils.HibernateSessionFactory;

public class HibernateUserDBService extends HibernateDBService<UserDataSet, Long> {

    public HibernateUserDBService(){
        super(UserDataSet.class);
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }
}
