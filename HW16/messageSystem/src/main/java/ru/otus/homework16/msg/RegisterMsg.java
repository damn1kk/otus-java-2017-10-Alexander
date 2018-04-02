package ru.otus.homework16.msg;

public class RegisterMsg extends Msg{
    public RegisterMsg(){
        super(RegisterMsg.class);
    }

    public RegisterMsg(String from, String to) {
        super(RegisterMsg.class, from, to);
    }
}
