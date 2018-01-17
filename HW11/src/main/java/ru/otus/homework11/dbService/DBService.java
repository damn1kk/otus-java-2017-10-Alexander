package ru.otus.homework11.dbService;

import ru.otus.homework11.DBException;

import java.io.Closeable;
import java.util.List;

public interface DBService <T, ID> extends Closeable{
    public void prepareTable() throws DBException;
    public void deleteTable() throws DBException;
    public List<T> findAll() throws DBException;
    public T findById(ID id) throws DBException;
    public void save(T object) throws DBException;
    public void update(T object) throws DBException;
    public void deleteById(ID id) throws DBException;
    public void delete(T object) throws DBException;
}
