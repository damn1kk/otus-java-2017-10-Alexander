package ru.otus.homework15;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;
    private Address frontAddress;
    private Address dbAddress;
    private Address queryGeneratorAddress;

    public MessageSystemContext(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress(){
        return frontAddress;
    }

    public Address getDbAddress(){
        return dbAddress;
    }

    public Address getQueryGeneratorAddress(){
        return queryGeneratorAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public void setQueryGeneratorAddress(Address queryGeneratorAddress) {
        this.queryGeneratorAddress = queryGeneratorAddress;
    }
}
