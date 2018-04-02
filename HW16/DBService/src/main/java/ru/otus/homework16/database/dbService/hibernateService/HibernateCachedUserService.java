package ru.otus.homework16.database.dbService.hibernateService;


import ru.otus.homework16.database.cach.CacheEngine;
import ru.otus.homework16.database.dataSet.UserDataSet;

public class HibernateCachedUserService extends HibernateCachedDBService<UserDataSet, Long> {

    public HibernateCachedUserService(CacheEngine<Long, UserDataSet> cache){
        super(UserDataSet.class, cache);
    }
}
