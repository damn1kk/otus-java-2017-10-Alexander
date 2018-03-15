package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public class MsgAnswerFromDBService extends MsgToGenerator{
    private String result;

    public MsgAnswerFromDBService(Address from, Address to, String result){
        super(from, to);
        this.result = result;
    }

    @Override
    public <T> void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.sendStringResultToFrontendService(result);
    }
}
