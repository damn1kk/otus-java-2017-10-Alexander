package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

import java.util.List;

public class MsgFindAll extends MsgToGenerator {

    public MsgFindAll(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.doFindAllQuery();
    }
}
