package ru.otus.homework06;

import ru.otus.homework06.ATM.ATM;
import ru.otus.homework06.ATM.Money.Banknote;
import ru.otus.homework06.ATM.Money.Cash;
import ru.otus.homework06.ATM.Money.Denomination;

public class Main {
    public static void main(String[] args) throws Exception{
        Banknote banknote = new Banknote(Denomination.TWO);
        Banknote banknote2 = new Banknote(Denomination.FIFTY);
        Banknote banknote3 = new Banknote(Denomination.TWO);
        Banknote banknote4 = new Banknote(Denomination.ONE);

        Cash startCashForATM = new Cash();
        startCashForATM.addBanknote(banknote);
        startCashForATM.addBanknote(banknote);
        startCashForATM.addBanknote(banknote3);
        for(int i = 0; i < 10; i++){
            startCashForATM.addBanknote(banknote2);
        }
        for(int i = 0; i < 20; i++){
            startCashForATM.addBanknote(banknote4);
        }

        ATM atm = new ATM();
        atm.transferCashToATM(startCashForATM);

        System.out.println("STARTING BALANCE: \n" + atm.getRemainingBalance());
        System.out.println("\nTAKEN MONEY FROM ATM: \n" + atm.withdrawal(73));
        System.out.println("\nATM BALANCE AFTER WITHDRAWAL: \n" + atm.getRemainingBalance());
        atm.transferCashToATM(new Cash(323));
        System.out.println("\nATM BALANCE AFTER TRANSFER CASH TO ATM: \n" + atm.getRemainingBalance());

    }
}
