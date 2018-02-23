package ru.otus.homework13.database.dbService.hibernateService;

import ru.otus.homework13.database.dataSet.UserDataSet;
import ru.otus.homework13.database.dbService.hibernateService.utils.HibernateSessionFactory;

public class HibernateUserDBService extends HibernateDBService<UserDataSet, Long> {

    public HibernateUserDBService(){
        super(UserDataSet.class);
        this.sessionFactory = HibernateSessionFactory.getSessionFactoryToUserDataSet();
    }
}
