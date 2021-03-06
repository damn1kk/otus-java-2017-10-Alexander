package ru.otus.homework15.messageSystem.messages;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;

import java.util.logging.Logger;

public abstract class Message {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final Address from;
    private final Address to;

    public Message(Address from, Address to){
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract <T> void  exec(Addressee addressee);
}
