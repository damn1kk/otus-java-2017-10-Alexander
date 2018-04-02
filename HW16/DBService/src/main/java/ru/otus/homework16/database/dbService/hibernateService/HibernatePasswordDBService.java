package ru.otus.homework16.database.dbService.hibernateService;

import ru.otus.homework16.database.dataSet.PasswordDataSet;
import ru.otus.homework16.database.dbService.DBException;

import java.util.List;
import java.util.logging.Logger;


public class HibernatePasswordDBService extends HibernateDBService<PasswordDataSet, Long> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public HibernatePasswordDBService(){
        super(PasswordDataSet.class);
    }

    public PasswordDataSet findPasswordByLogin(String login) throws DBException {
        notifyQueryListeners("Find password by login: " + login);

        PasswordDataSet passwordDataSet = runInSessionWithFunction(session -> {
            org.hibernate.query.Query query = session.createQuery("from PasswordDataSet where login = :loginVar ");
            query.setParameter("loginVar", login);
            List<PasswordDataSet> list = query.getResultList();

            return list.isEmpty() ? null : list.get(0);
        });

        return passwordDataSet;
    }
}
