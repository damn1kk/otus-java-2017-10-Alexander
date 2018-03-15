package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public class MsgStart extends MsgToGenerator {
    public MsgStart(Address from, Address to){
        super(from, to);
    }

    @Override
    public void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.start();
    }
}
