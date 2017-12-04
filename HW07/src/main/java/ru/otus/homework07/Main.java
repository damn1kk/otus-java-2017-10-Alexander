package ru.otus.homework07;

import ru.otus.homework07.ATM.ATM;
import ru.otus.homework07.ATM.Money.Cash;
import ru.otus.homework07.ATMDepartment.ATMDepartment;

public class Main {
    public static void main(String[] args) throws Exception{

        ATM atm1 = new ATM();
        atm1.transferCashToATM(new Cash(5300));
        ATM atm2 = new ATM();
        atm2.transferCashToATM(new Cash(535));
        ATM atm3 = new ATM();
        atm3.transferCashToATM(new Cash(4200));

        ATMDepartment department = new ATMDepartment();
        department.addAtm(atm1);
        department.addAtm(atm2);
        department.addAtm(atm3);

        System.out.println("Remaining balance from all ATMs: " +
                department.getRemainingBalanceCost());

        atm1.transferCashToATM(new Cash(335));
        atm2.transferCashToATM(new Cash(434));

        System.out.println("Remaining balance from all ATMs after transfer money: " +
                department.getRemainingBalanceCost());

        department.restoreDefaultATMState();

        System.out.println("Remaining balance from all ATMs after restore default state: " +
                department.getRemainingBalanceCost());
    }


}
