package ru.otus.homework15.database.dbService.hibernateService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.DataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.database.dbService.hibernateService.utils.HibernateSessionFactory;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.MessageSystem;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toQueryListener.MsgNotifyQueryListener;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateDBService <T extends DataSet, ID extends Serializable> implements DBService<T, ID> {
    protected final Address address;
    protected final MessageSystemContext messageSystemContext;

    protected Set<QueryListener> queryListenerSet = new HashSet<>();

    protected SessionFactory sessionFactory;
    protected static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";
    private Class<T> type;

    public HibernateDBService(Class<T> type, Address address, MessageSystemContext messageSystemContext){
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
        this.type = type;
        this.address = address;
        this.messageSystemContext = messageSystemContext;

        this.messageSystemContext.getMessageSystem().addAddressee(this);
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

            notifyQueryListeners("Delete table : " + tableName);
        });
    }

    @Override
    public List<T> findAll() throws DBException {
        return runInSessionWithFunction(session ->{
            CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);

            notifyQueryListeners("Find all");

            List<T> resultList = session.createQuery(query).getResultList();

            return resultList;
        });
    }

    @Override
    public T findById(ID id) throws DBException {
        notifyQueryListeners("Find by ID : " + id);

        T result = runInSessionWithFunction(session -> session.get(type,id));

        return result;
    }

    @Override
    public void deleteById(ID id) throws DBException {
        notifyQueryListeners("Delete by ID: " + id);

        runInSessionWithConsumer(session -> {
            T object = session.get(type, id);
            if(object != null) {
                session.delete(object);
            }
        });
    }

    @Override
    public void delete(T object) throws DBException {
        notifyQueryListeners("Delete object: " + object.toString());

        runInSessionWithConsumer(session ->
            session.delete(object)
        );
    }

    @Override
    public void save(T object) throws DBException {
        notifyQueryListeners("Save object: " + object.toString());

        runInSessionWithConsumer(session -> session.save(object));
    }

    @Override
    public void update(T object) throws DBException {
        notifyQueryListeners("Update object: " + object.toString());
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
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
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
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
            throw new DBException(e);
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }

    @Override
    public void addQueryListener(QueryListener queryListener) {
        queryListenerSet.add(queryListener);
    }

    @Override
    public void deleteQueryListener(QueryListener queryListener){
        queryListenerSet.remove(queryListener);
    }

    protected void notifyQueryListeners(String query){
        for(QueryListener listener : queryListenerSet) {
            if (listener instanceof Addressee) {
                Address listenersAddress = ((Addressee)listener).getAddress();

                Message message = new MsgNotifyQueryListener(
                        getAddress(), listenersAddress, query
                );

                getMS().sendMessage(message);
            }
        }
    }


}
