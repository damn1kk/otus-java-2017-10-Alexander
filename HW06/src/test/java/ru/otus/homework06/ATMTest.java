package ru.otus.homework06;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework06.ATM.ATM;
import ru.otus.homework06.ATM.Money.Banknote;
import ru.otus.homework06.ATM.Money.Cash;
import ru.otus.homework06.ATM.Money.CashException;
import ru.otus.homework06.ATM.Money.Denomination;

import java.util.HashMap;
import java.util.Map;


public class ATMTest {

    private ATM atm;

    @Before
    public void initEmptyATM(){
        atm = new ATM();
    }

    @Test
    public void transferCashToATMTest() throws CashException{
        long testCost = 3245;
        Cash cash = new Cash(testCost);

        atm.transferCashToATM(cash);

        Assert.assertEquals(testCost, atm.getRemainingBalanceCost());
    }

    @Test
    public void withdrawalTest() throws CashException{
        long startCashForATM = 42353521;
        Cash cash = new Cash(startCashForATM);

        atm.transferCashToATM(cash);

        long testValue = 3521;
        Cash takenCash = atm.withdrawal(testValue);
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(new Banknote(Denomination.TWO_THOUSAND), 1);
        map.put(new Banknote(Denomination.ONE_THOUSAND), 1);
        map.put(new Banknote(Denomination.FIVE_HUNDRED), 1);
        map.put(new Banknote(Denomination.TEN), 2);
        map.put(new Banknote(Denomination.ONE), 1);


        Assert.assertEquals(testValue, takenCash.getCost());
        Assert.assertEquals(map, takenCash.getBanknotes());
        Assert.assertEquals(startCashForATM - testValue, atm.getRemainingBalanceCost());
    }

    @Test(expected = CashException.class)
    public void withdrawal_takeMoreThanATMHas_ExceptionThrown() throws CashException{
        Cash startCashForATM = new Cash(3040);
        atm.transferCashToATM(startCashForATM);

        Cash takenCash = atm.withdrawal(4000);
    }

    @Test(expected = CashException.class)
    public void withdrawal_takeBanknoteWhoseATMHasNot_ExceptionThrown() throws CashException{
        Cash startCashForATM = new Cash(4444);
        atm.transferCashToATM(startCashForATM);

        Cash takenCash = atm.withdrawal(345);
    }
}
