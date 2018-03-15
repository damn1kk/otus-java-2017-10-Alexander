package ru.otus.homework15.messageSystem.messages.toQueryListener;

import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.messageSystem.Address;

public class MsgNotifyQueryListener extends MsgToQueryListener {
    private String queryString;

    public MsgNotifyQueryListener(Address from, Address to, String queryString){
        super(from, to);
        this.queryString = queryString;
    }

    @Override
    public void exec(QueryListener queryListener) {
        queryListener.getNewQuery(queryString);
    }
}
