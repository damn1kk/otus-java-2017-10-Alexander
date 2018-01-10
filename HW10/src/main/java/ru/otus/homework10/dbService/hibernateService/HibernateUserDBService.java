package ru.otus.homework10.dbService.hibernateService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.otus.homework10.DBException;
import ru.otus.homework10.dataSet.UserDataSet;
import ru.otus.homework10.dbService.DBService;
import ru.otus.homework10.dbService.hibernateService.utils.HibernateSessionFactory;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUserDBService implements DBService<UserDataSet, Long> {

    private SessionFactory sessionFactory;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";

    public HibernateUserDBService(){
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    @Override
    public void prepareTable() throws DBException{
    }

    @Override
    public void deleteTable() throws DBException {
        runInSessionWithConsumer(session -> {
            Table table = UserDataSet.class.getAnnotation(Table.class);
            String tableName = table.name();

            Query query = session.createNativeQuery(String.format(DROP_TABLE, tableName));
            query.executeUpdate();
        });
    }

    @Override
    public List<UserDataSet> findAll() throws DBException {
        return runInSessionWithFunction(session ->{
            CriteriaQuery<UserDataSet> query = session.getCriteriaBuilder().createQuery(UserDataSet.class);
            Root<UserDataSet> root = query.from(UserDataSet.class);
            query.select(root);

            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public UserDataSet findById(Long id) throws DBException {
        return runInSessionWithFunction(session -> session.get(UserDataSet.class, id));
    }

    @Override
    public void save(UserDataSet user) throws DBException {
        runInSessionWithConsumer(session -> session.save(user));

    }

    @Override
    public void update(UserDataSet user) throws DBException {
        runInSessionWithConsumer(session -> session.update(user));
    }

    @Override
    public void deleteById(Long id) throws DBException {
        runInSessionWithConsumer(session -> {
            UserDataSet user = session.get(UserDataSet.class, id);
            if(user != null) {
                session.delete(user);
            }
        });
    }

    @Override
    public void delete(UserDataSet user) throws DBException {
        runInSessionWithConsumer(session -> {
            UserDataSet userTemp = session.get(UserDataSet.class, user.getId());
            if(userTemp != null) {
                session.delete(user);
            }
        });

    }

    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }

    private <R> R runInSessionWithFunction(Function<Session, R> function) throws DBException{
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }catch(Exception e){
            if(transaction != null) transaction.rollback();
            throw new DBException(e);
        }
    }

    private void runInSessionWithConsumer(Consumer<Session> consumer) throws DBException{
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null) transaction.rollback();
            throw new DBException(e);
        }
    }
}
