package ru.otus.homework16.msg;

import java.util.Set;

public class AllDbServicesMsg extends Msg {
    private Set<String> dbServices;

    public AllDbServicesMsg(String from, String to, Set<String> dbServices) {
        super(AllDbServicesMsg.class, from, to);
        this.dbServices = dbServices;
    }

    public Set<String> getDbServices(){
        return dbServices;
    }
}
