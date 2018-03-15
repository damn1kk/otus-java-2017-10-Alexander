package ru.otus.homework15.database.dbService.hibernateService;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.cach.CacheEngine;
import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.messageSystem.Address;

public class HibernateCachedUserService extends HibernateCachedService<UserDataSet, Long> {

    public HibernateCachedUserService(CacheEngine<Long, UserDataSet> cache, Address address, MessageSystemContext context){
        super(UserDataSet.class, cache, address, context);
    }
}
