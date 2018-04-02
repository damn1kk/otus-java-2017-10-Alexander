package ru.otus.homework16.msg.forDBService;

import ru.otus.homework16.msg.Msg;

public class RegisterNewUserMsg extends Msg {
    private String login;
    private String password;

    public RegisterNewUserMsg(String from, String to, String login, String password){
        super(RegisterNewUserMsg.class, from, to);
        this.login = login;
        this.password = password;
    }

    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
}
