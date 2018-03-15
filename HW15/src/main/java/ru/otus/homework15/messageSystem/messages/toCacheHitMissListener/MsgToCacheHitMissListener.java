package ru.otus.homework15.messageSystem.messages.toCacheHitMissListener;

import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.messages.Message;

public abstract class MsgToCacheHitMissListener extends Message {

    public MsgToCacheHitMissListener(Address from, Address to){
        super(from, to);
    }

    @Override
    public <T> void exec(Addressee addressee) {
        if(addressee instanceof CacheHitMissListener){
            exec((CacheHitMissListener)addressee);
        }
    }

    public abstract void exec(CacheHitMissListener listener);
}
