package ru.otus.homework15.messageSystem.messages.toQueryListener;

import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.messages.Message;

public abstract class MsgToQueryListener extends Message {
    public MsgToQueryListener(Address from, Address to){
        super(from, to);
    }

    @Override
    public <T> void exec(Addressee addressee) {
        if(addressee instanceof QueryListener){
            exec((QueryListener)addressee);
        }
    }

    public abstract void exec(QueryListener queryListener);
}
