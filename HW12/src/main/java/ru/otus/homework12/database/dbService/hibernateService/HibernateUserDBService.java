package ru.otus.homework12.database.dbService.hibernateService;

import ru.otus.homework12.database.dbService.hibernateService.utils.HibernateSessionFactory;
import ru.otus.homework12.database.dataSet.UserDataSet;

public class HibernateUserDBService extends HibernateDBService<UserDataSet, Long> {

    public HibernateUserDBService(){
        super(UserDataSet.class);
        this.sessionFactory = HibernateSessionFactory.getSessionFactoryToUserDataSet();
    }
}
