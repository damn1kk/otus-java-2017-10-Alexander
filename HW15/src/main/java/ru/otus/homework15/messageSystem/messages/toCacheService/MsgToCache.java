package ru.otus.homework15.messageSystem.messages.toCacheService;

import ru.otus.homework15.database.dbService.CachedService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.messages.Message;

public abstract class MsgToCache extends Message{
    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public <T> void exec(Addressee addressee) {
        if(addressee instanceof CachedService){
            exec((CachedService) addressee);
        }
    }

    public abstract <T> void exec(CachedService cachedService);
}
