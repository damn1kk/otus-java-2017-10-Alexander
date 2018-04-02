package ru.otus.homework16.database.dbService.hibernateService;

import ru.otus.homework16.database.cach.CacheEngine;
import ru.otus.homework16.database.dataSet.PasswordDataSet;

public class HibernateCachedPasswordDBService extends HibernateCachedDBService<PasswordDataSet, Long>{

    public HibernateCachedPasswordDBService(CacheEngine<Long, PasswordDataSet> cache){
        super(PasswordDataSet.class, cache);
    }
}
