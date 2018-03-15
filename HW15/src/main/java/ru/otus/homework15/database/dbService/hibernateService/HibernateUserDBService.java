package ru.otus.homework15.database.dbService.hibernateService;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.database.dbService.hibernateService.utils.HibernateSessionFactory;
import ru.otus.homework15.messageSystem.Address;

public class HibernateUserDBService extends HibernateDBService<UserDataSet, Long> {

    public HibernateUserDBService(Address address, MessageSystemContext context){
        super(UserDataSet.class, address, context);
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }
}
