package ru.otus.homework07.ATM.Money;

public class CashException extends Exception {
    public CashException(){
        super();
    }

    public CashException(String text){
        super(text);
    }
}
