package ru.otus.homework15.queryGenerator;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.database.dbService.hibernateService.HibernateCachedService;
import ru.otus.homework15.messageSystem.Address;

public class QueryGeneratorUserDataSet extends QueryGeneratorImpl<UserDataSet, Long> {
    public QueryGeneratorUserDataSet(Address address, MessageSystemContext msContext){
        super(UserDataSet.class, address, msContext);
    }
}
