package ru.otus.homework16.msg.forDBService;

import ru.otus.homework16.msg.Msg;

public class FindPassByLoginMsg extends Msg {
    private String login;
    private String password;

    public FindPassByLoginMsg(String from, String to, String login, String password){
        super(FindPassByLoginMsg.class, from, to);
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
