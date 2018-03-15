package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.messageSystem.Address;

public class MsgDeleteQueryListener extends MsgToDB {
    QueryListener queryListener;

    public MsgDeleteQueryListener(Address from, Address to, QueryListener queryListener){
        super(from, to);
        this.queryListener = queryListener;
    }

    @Override
    public <T> void exec(DBService dbService) {
        dbService.deleteQueryListener(queryListener);
    }
}
