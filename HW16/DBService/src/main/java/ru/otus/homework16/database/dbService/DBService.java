package ru.otus.homework16.database.dbService;


import ru.otus.homework16.database.dataSet.DataSet;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;

public interface DBService <T extends DataSet, ID extends Serializable> extends Closeable{
    public void prepareTable() throws DBException;
    public void deleteTable() throws DBException;
    public List<T> findAll() throws DBException;
    public T findById(ID id) throws DBException;
    public void save(T object) throws DBException;
    public void update(T object) throws DBException;
    public void deleteById(ID id) throws DBException;
    public void delete(T object) throws DBException;

    public void addQueryListener(QueryListener queryListener);
    public void deleteQueryListener(QueryListener queryListener);
}
