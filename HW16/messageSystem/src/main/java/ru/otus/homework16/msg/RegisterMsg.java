package ru.otus.homework16.msg;

import ru.otus.homework16.addressee.TypeOfAddressee;

public class RegisterMsg extends Msg{
    private TypeOfAddressee typeOfAddressee;

    public RegisterMsg(String from, String to, TypeOfAddressee typeOfAddressee) {
        super(RegisterMsg.class, from, to);
        this.typeOfAddressee = typeOfAddressee;
    }

    public TypeOfAddressee getTypeOfAddressee() {
        return typeOfAddressee;
    }
}
