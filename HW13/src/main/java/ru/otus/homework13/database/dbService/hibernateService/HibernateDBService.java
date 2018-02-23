package ru.otus.homework13.database.dbService.hibernateService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.otus.homework13.database.dbService.DBException;
import ru.otus.homework13.database.dbService.DBService;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateDBService <T, ID extends Serializable> implements DBService<T, ID> {
    protected SessionFactory sessionFactory;
    protected static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";
    private Class<T> type;

    public HibernateDBService(Class<T> type){
        this.type = type;
    }

    @Override
    public void prepareTable() throws DBException {
    }

    @Override
    public void deleteTable() throws DBException {
        runInSessionWithConsumer(session -> {
            Table table = type.getAnnotation(Table.class);
            String tableName = table.name();

            Query query = session.createNativeQuery(String.format(DROP_TABLE, tableName));
            query.executeUpdate();
        });
    }

    @Override
    public List<T> findAll() throws DBException {
        return runInSessionWithFunction(session ->{
            CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);

            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public T findById(ID id) throws DBException {
        return runInSessionWithFunction(session -> session.get(type, id));
    }

    @Override
    public void deleteById(ID id) throws DBException {
        runInSessionWithConsumer(session -> {
            T object = session.get(type, id);
            if(object != null) {
                session.delete(object);
            }
        });
    }

    @Override
    public void delete(T object) throws DBException {
        runInSessionWithConsumer(session ->
            session.delete(object)
        );
    }

    @Override
    public void save(T object) throws DBException {
        runInSessionWithConsumer(session -> session.save(object));
    }

    @Override
    public void update(T object) throws DBException {
        runInSessionWithConsumer(session -> session.update(object));
    }

    @Override
    public void close() throws IOException {
        sessionFactory.close();
    }

    protected  <R> R runInSessionWithFunction(Function<Session, R> function) throws DBException {
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

    protected void runInSessionWithConsumer(Consumer<Session> consumer) throws DBException{
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
