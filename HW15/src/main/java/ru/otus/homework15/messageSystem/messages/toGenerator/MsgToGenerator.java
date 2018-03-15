package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public abstract class MsgToGenerator extends Message {
    public MsgToGenerator(Address from, Address to){
        super(from,to);
    }

    @Override
    public <T> void exec(Addressee addressee) {
        if(addressee instanceof QueryGeneratorService){
            exec((QueryGeneratorService) addressee);
        }
    }

    public abstract <T> void exec(QueryGeneratorService queryGeneratorService);

}
