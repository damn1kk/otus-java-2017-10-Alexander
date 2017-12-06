package ru.otus.homework07.ATMDepartment;

import ru.otus.homework07.ATM.ATM;
import ru.otus.homework07.ATM.Money.Cash;
import ru.otus.homework07.ATM.Money.CashException;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartment {
    private Set<ATM> atmSet = new HashSet<>();

    public ATMDepartment(){
    }

    public Set<ATM> getAtmSet() {
        return atmSet;
    }

    public void addAtm(ATM atm) throws CashException{
        if(atm == null){
            throw new CashException("Your atm is null");
        }
        atmSet.add(atm);
        atm.saveATMState();
    }

    public void removeAtm(ATM atm) throws CashException{
        if(atm == null){
            throw new CashException("Your atm is null");
        }
        atmSet.remove(atm);
    }

    public Cash getRemainingBalance(){
        Cash result = new Cash();
        for(ATM atm : atmSet){
            result.addAnotherCash(atm.getRemainingBalance());
        }
        return result;
    }

    public long getRemainingBalanceCost(){
        return getRemainingBalance().getCost();
    }

    public void restoreDefaultATMState(){
        for(ATM atm : atmSet){
            atm.restore();
        }
    }

    public void saveATMsStateAsDefault() throws CashException{
        for(ATM atm : atmSet){
            atm.saveATMState();
        }
    }

    public int numberOfAtms(){
        return atmSet.size();
    }

}
