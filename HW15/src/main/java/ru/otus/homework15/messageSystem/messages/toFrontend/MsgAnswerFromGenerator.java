package ru.otus.homework15.messageSystem.messages.toFrontend;

import ru.otus.homework15.front.FrontendService;
import ru.otus.homework15.messageSystem.Address;

public class MsgAnswerFromGenerator extends MsgToFrontend{
    private String result;

    public MsgAnswerFromGenerator(Address from, Address to, String result){
        super(from, to);
        this.result = result;
    }

    @Override
    public <T> void exec(FrontendService frontendService) {
        frontendService.sendQueryStringResultToClients(result);
    }
}
