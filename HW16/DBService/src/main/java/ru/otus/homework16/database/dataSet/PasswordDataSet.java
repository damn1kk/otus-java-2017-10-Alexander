package ru.otus.homework16.database.dataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORD")
public class PasswordDataSet extends DataSet{
    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    public PasswordDataSet(){}
    public PasswordDataSet(String login, String password){
        super(-1L);
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
