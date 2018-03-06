package ru.otus.homework15.database.dbService.hibernateService;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.PasswordDataSet;
import ru.otus.homework15.database.dbService.hibernateService.utils.HibernateSessionFactory;
import ru.otus.homework15.messageSystem.Address;

import java.util.logging.Logger;


public class HibernatePasswordDBService extends HibernateDBService<PasswordDataSet, String> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public HibernatePasswordDBService(Address address, MessageSystemContext context){
        super(PasswordDataSet.class, address, context);
        this.sessionFactory = HibernateSessionFactory.getSessionFactoryToPassword();
    }

}
