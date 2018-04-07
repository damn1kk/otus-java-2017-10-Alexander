package ru.otus.homework16.msg;

public class AnswerRegisterMsg extends Msg {
    public AnswerRegisterMsg(String from, String to) {
        super(AnswerRegisterMsg.class, from, to);
    }
}
