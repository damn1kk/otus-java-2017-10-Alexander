package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public class MsgFindByRandomId extends MsgToGenerator {
    public MsgFindByRandomId(Address from, Address to){
        super(from, to);
    }

    @Override
    public void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.doFindByRandomIdQuery();
    }
}
