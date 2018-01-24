package ru.otus.homework12.database.dbService.hibernateService;

import ru.otus.homework12.database.dbService.CachedDBService;
import ru.otus.homework12.database.dbService.DBException;
import ru.otus.homework12.database.cach.Cache;
import ru.otus.homework12.database.cach.CacheEngine;
import ru.otus.homework12.database.cach.MyElement;
import ru.otus.homework12.database.dataSet.UserDataSet;

import java.io.IOException;
import java.util.List;

public class HibernateCachedUserDBService extends HibernateUserDBService implements CachedDBService {
    CacheEngine<Long, UserDataSet> cache = new Cache<>(10);

    public HibernateCachedUserDBService(){
        super();
    }

    @Override
    public int getCacheHit(){
        return cache.getHitCount();
    }
    @Override
    public int getCacheMiss(){
        return cache.getMissCount();
    }

    @Override
    public UserDataSet findById(Long id) throws DBException {
        MyElement<Long, UserDataSet> element = cache.get(id);
        if(element != null){
            return element.getValue();
        }else {
            UserDataSet user = super.findById(id);
            cache.put(new MyElement<>(user.getId(), user));
            return user;
        }
    }

    @Override
    public void save(UserDataSet user) throws DBException {
        MyElement<Long, UserDataSet> element = new MyElement<>(user.getId(), user);
        super.save(user);
        cache.put(element);
    }

    @Override
    public void update(UserDataSet user) throws DBException {
        MyElement<Long, UserDataSet> element = new MyElement<>(user.getId(), user);
        super.update(user);
        cache.put(element);
    }

    @Override
    public void deleteById(Long id) throws DBException {
        cache.delete(id);
        super.deleteById(id);
    }

    @Override
    public void delete(UserDataSet user) throws DBException {
        cache.delete(user.getId());
        super.delete(user);
    }

    @Override
    public List<UserDataSet> findAll() throws DBException {
        List<UserDataSet> result =  super.findAll();
        for(UserDataSet user : result){
            cache.put(new MyElement<>(user.getId(), user));
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        cache.dispose();
        super.close();
    }


}
