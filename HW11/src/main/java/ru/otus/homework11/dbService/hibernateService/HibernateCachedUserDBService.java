package ru.otus.homework11.dbService.hibernateService;

import ru.otus.homework11.DBException;
import ru.otus.homework11.cach.Cache;
import ru.otus.homework11.cach.CacheEngine;
import ru.otus.homework11.cach.MyElement;
import ru.otus.homework11.dataSet.UserDataSet;

import java.io.IOException;
import java.util.List;

public class HibernateCachedUserDBService extends HibernateUserDBService {
    CacheEngine<Long, UserDataSet> cache = new Cache<>(10);

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
        cache.put(element);
        super.save(user);
    }

    @Override
    public void update(UserDataSet user) throws DBException {
        MyElement<Long, UserDataSet> element = new MyElement<>(user.getId(), user);
        cache.put(element);
        super.update(user);
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
