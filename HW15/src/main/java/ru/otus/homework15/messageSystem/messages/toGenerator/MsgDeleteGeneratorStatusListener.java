package ru.otus.homework15.messageSystem.messages.toGenerator;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.queryGenerator.GeneratorStatusListener;
import ru.otus.homework15.queryGenerator.QueryGeneratorService;

public class MsgDeleteGeneratorStatusListener extends MsgToGenerator {
    private GeneratorStatusListener statusListener;

    public MsgDeleteGeneratorStatusListener(Address from, Address to, GeneratorStatusListener statusListener){
        super(from, to);
        this.statusListener = statusListener;
    }

    @Override
    public <T> void exec(QueryGeneratorService queryGeneratorService) {
        queryGeneratorService.deleteGeneratorStatusListener(statusListener);
    }
}
