package ru.otus.homework07;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework07.ATM.ATM;
import ru.otus.homework07.ATM.Money.Cash;
import ru.otus.homework07.ATM.Money.CashException;
import ru.otus.homework07.ATMDepartment.ATMDepartment;

import java.util.Random;

public class ATMDepartmentTest {
    private ATMDepartment department;

    @Before
    public void initDepartment(){
        department = new ATMDepartment();
    }

    @Test
    public void addAtmTest() throws Exception{
        Random rnd = new Random();
        int numberOfAtm = rnd.nextInt(100) + 5;
        for(int i = 0; i < numberOfAtm; i++){
            int randomCash = rnd.nextInt(1000) + 10;
            department.addAtm(new ATM(new Cash(randomCash)));
        }

        Assert.assertEquals(numberOfAtm, department.numberOfAtms());
    }

    @Test(expected = CashException.class)
    public void addAtmTest_addAtmNull_ExceptionThrown() throws Exception{
        department.addAtm(null);
    }

    @Test
    public void removeAtmTest() throws Exception{
        department.addAtm(new ATM());
        ATM atm2 = new ATM();
        department.addAtm(atm2);
        department.addAtm(new ATM());

        int sizeBeforeRemove = department.numberOfAtms();
        department.removeAtm(atm2);
        int sizeAfterRemove = department.numberOfAtms();

        Assert.assertEquals(2, department.numberOfAtms());
    }

    @Test(expected = CashException.class)
    public void removeAtmTest_removeAtmNull_ExceptionThrown() throws Exception{
        department.removeAtm(null);
    }

    @Test
    public void getRemainingBalanceCostTest() throws Exception{
        department.addAtm(new ATM(new Cash(233)));
        department.addAtm(new ATM(new Cash(433)));
        department.addAtm(new ATM(new Cash(959)));

        Assert.assertEquals(233 + 433 + 959, department.getRemainingBalanceCost());

    }


}
