package ru.otus.homework07.ATMDepartment;

import ru.otus.homework07.ATM.ATM;
import ru.otus.homework07.ATM.Money.Cash;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartment {
    private Set<ATM> atmSet = new HashSet<>();

    public ATMDepartment(){
    }

    public Set<ATM> getAtmSet() {
        return atmSet;
    }

    public void addAtm(ATM atm){
        atmSet.add(atm);
        atm.saveATMState();
    }

    public void removeAtm(ATM atm){
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

    public void saveATMsStateAsDefault(){
        for(ATM atm : atmSet){
            atm.saveATMState();
        }
    }

}
