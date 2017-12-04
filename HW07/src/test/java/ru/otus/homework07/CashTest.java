package ru.otus.homework07;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework07.ATM.Money.Banknote;
import ru.otus.homework07.ATM.Money.Cash;
import ru.otus.homework07.ATM.Money.CashException;
import ru.otus.homework07.ATM.Money.Denomination;

import java.util.HashMap;
import java.util.Map;

public class CashTest {
    private Cash cash;
    private Banknote b1, b2, b5, b10, b50, b100, b200, b500, b1000, b2000, b5000;

    @Before
    public void initBanknotes(){
        b1 = new Banknote(Denomination.ONE);
        b2 = new Banknote(Denomination.TWO);
        b5 = new Banknote(Denomination.FIVE);
        b10 = new Banknote(Denomination.TEN);
        b50 = new Banknote(Denomination.FIFTY);
        b100 = new Banknote(Denomination.ONE_HUNDRED);
        b200 = new Banknote(Denomination.TWO_HUNDRED);
        b500 = new Banknote(Denomination.FIVE_HUNDRED);
        b1000 = new Banknote(Denomination.ONE_THOUSAND);
        b2000 = new Banknote(Denomination.TWO_THOUSAND);
        b5000 = new Banknote(Denomination.FIVE_THOUSAND);
    }

    @Before
    public void initEmptyCash(){
        cash = new Cash();
    }

    @Test
    public void cashConstructorFromCost() throws CashException {
        int testValue = 3000;
        cash = new Cash(testValue);
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(b1000, 1);
        map.put(b2000, 1);

        Assert.assertEquals(testValue, cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());

        testValue = 8868;
        cash = new Cash(testValue);
        map.clear();
        for(Denomination d : Denomination.values()){
            map.put(new Banknote(d), 1);
        }

        Assert.assertEquals(testValue, cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());

    }

    @Test(expected = CashException.class)
    public void cashConstrucotrFromCost_withNegativeValue_ExceptionThrown() throws CashException{
        cash = new Cash(-5);
    }

    @Test
    public void addBanknote(){
        Banknote testBanknote = b50;
        cash.addBanknote(testBanknote);
        cash.addBanknote(testBanknote);
        Map<Banknote, Integer> map= new HashMap<>();
        map.put(testBanknote, 2);

        Assert.assertEquals(testBanknote.getCost() * 2, cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());
    }

    @Test
    public void takeOutBanknote_takeLastBanknote() throws Exception{
        cash.addBanknote(b50);
        cash.addBanknote(b1);
        cash.addBanknote(b1000);
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(b1, 1);
        map.put(b50, 1);

        cash.takeOutABanknote(new Banknote(Denomination.ONE_THOUSAND));

        Assert.assertEquals(b1.getCost() + b50.getCost(), cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());
    }

    @Test
    public void takeOutBanknote_takeNotLastBanknote() throws CashException{
        cash.addBanknote(b1);
        cash.addBanknote(b1);
        cash.addBanknote(b1);
        cash.addBanknote(b50);
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(b1, 2);
        map.put(b50, 1);

        cash.takeOutABanknote(b1);

        Assert.assertEquals(b1.getCost() * 2 + b50.getCost(), cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());
    }

    @Test(expected = CashException.class)
    public void takeOutBanknote_nonExistingBanknote_ExceptionThrown() throws CashException{
        cash.addBanknote(b50);
        cash.addBanknote(b1);
        cash.addBanknote(b1000);

        cash.takeOutABanknote(new Banknote(Denomination.TWO));
    }

    @Test
    public void hasBanknote(){
        cash.addBanknote(b1);
        cash.addBanknote(b50);
        cash.addBanknote(b1000);

        Assert.assertTrue(cash.hasBanknote(new Banknote(Denomination.FIFTY)));
        Assert.assertFalse(cash.hasBanknote(new Banknote(Denomination.TWO)));
    }


    @Test
    public void makeChangeCostToBanknotesFromCashTest() throws CashException{
        cash.addBanknote(b50);
        for(int i = 0; i < 100; i++){
            cash.addBanknote(b2);
        }
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(b50, 1);
        map.put(b2, 2);

        int testValueCost = 54;
        Cash testCash = cash.makeChangeCostToBanknotesFromCash(testValueCost, cash);

        Assert.assertEquals(testValueCost, testCash.getCost());
        Assert.assertEquals(map, testCash.getBanknotes());
    }

    @Test(expected = CashException.class)
    public void makeChangeCostToBanknotesFromCash_ExceptionThrown() throws CashException{
        cash.addBanknote(b50);
        cash.addBanknote(b2);

        int testValueCost = 54;
        Cash testCash = cash.makeChangeCostToBanknotesFromCash(testValueCost, cash);

    }

    @Test
    public void addAnotherCashTest(){
        cash.addBanknote(b50);
        cash.addBanknote(b100);
        long expectedCost = cash.getCost();

        Cash anotherCash = new Cash();
        anotherCash.addBanknote(b500);
        anotherCash.addBanknote(b100);
        expectedCost += anotherCash.getCost();

        Map<Banknote, Integer> map = new HashMap<>();
        map.put(b50, 1);
        map.put(b100, 2);
        map.put(b500, 1);

        cash.addAnotherCash(anotherCash);

        Assert.assertEquals(expectedCost, cash.getCost());
        Assert.assertEquals(map, cash.getBanknotes());
    }

}
