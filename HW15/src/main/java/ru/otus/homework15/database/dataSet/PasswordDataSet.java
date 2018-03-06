package ru.otus.homework15.database.dataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Password")
public class PasswordDataSet {
    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public PasswordDataSet(){}
    public PasswordDataSet(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
