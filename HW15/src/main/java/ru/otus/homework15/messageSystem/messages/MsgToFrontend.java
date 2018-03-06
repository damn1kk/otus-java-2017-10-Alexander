package ru.otus.homework15.messageSystem.messages;

import ru.otus.homework15.front.FrontendService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if(addressee instanceof FrontendService){
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}
