package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public class MsgSaveNewRandomDataSet extends MsgToGenerator{
    public MsgSaveNewRandomDataSet(Address from, Address to){
        super(from, to);
    }

    @Override
    public <T> void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.doSaveNewDataSetQuery();
    }
}
