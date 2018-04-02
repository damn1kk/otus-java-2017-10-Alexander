package ru.otus.homework16.msg.forDBService;

import ru.otus.homework16.msg.Msg;

public class AnswerFindPassByLoginMsg extends Msg {
    private String login;
    private String password;
    private boolean success;
    private String reasonOfError;

    public AnswerFindPassByLoginMsg(String from, String to, String login, String password, boolean success, String reasonOfError){
        super(AnswerFindPassByLoginMsg.class, from, to);
        this.login = login;
        this.password = password;
        this.success = success;
        this.reasonOfError = reasonOfError;
    }

    public String getPassword(){
        return password;
    }

    public String getLogin(){
        return login;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReasonOfError() {
        return reasonOfError;
    }
}
