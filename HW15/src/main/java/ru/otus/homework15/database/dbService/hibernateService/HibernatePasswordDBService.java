package ru.otus.homework15.database.dbService.hibernateService;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.PasswordDataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.hibernateService.utils.HibernateSessionFactory;
import ru.otus.homework15.messageSystem.Address;

import java.util.List;
import java.util.logging.Logger;


public class HibernatePasswordDBService extends HibernateDBService<PasswordDataSet, Long> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public HibernatePasswordDBService(Address address, MessageSystemContext context){
        super(PasswordDataSet.class, address, context);
    }

    public PasswordDataSet findPasswordByLogin(String login) throws DBException{
        notifyQueryListeners("Find password by login: " + login);

        PasswordDataSet result = runInSessionWithFunction(session -> {
            org.hibernate.query.Query query = session.createQuery("from PasswordDataSet where login = :loginVar ");
            query.setParameter("loginVar", login);
            List<PasswordDataSet> list = query.getResultList();

            return list.isEmpty() ? null : list.get(0);
        });

        return result;
    }
}
