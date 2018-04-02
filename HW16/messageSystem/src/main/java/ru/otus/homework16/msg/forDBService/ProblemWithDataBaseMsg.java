package ru.otus.homework16.msg.forDBService;

import ru.otus.homework16.msg.Msg;

public class ProblemWithDataBaseMsg extends Msg{
    private String exceptionMessage;

    public ProblemWithDataBaseMsg(String from, String to, String exceptionMessage){
        super(ProblemWithDataBaseMsg.class, from, to);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage(){
        return exceptionMessage;
    }

}
