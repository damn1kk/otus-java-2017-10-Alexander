package ru.otus.homework15.messageSystem.messages;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;

public abstract class MsgToGenerator extends Message{
    public MsgToGenerator(Address from, Address to){
        super(from,to);
    }

    @Override
    public void exec(Addressee addressee) {

    }
}
