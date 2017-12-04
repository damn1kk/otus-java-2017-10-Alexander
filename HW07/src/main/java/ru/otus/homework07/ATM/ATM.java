package ru.otus.homework07.ATM;

import ru.otus.homework07.ATM.Money.Banknote;
import ru.otus.homework07.ATM.Money.Cash;
import ru.otus.homework07.ATM.Money.CashException;

public class ATM {
    private Cash remainingBalance;
    private ATMState savedState;

    public ATM() {
        remainingBalance = new Cash();
        saveATMState();
    }

    public Cash getRemainingBalance(){
        return remainingBalance;
    }

    public long getRemainingBalanceCost(){
        return remainingBalance.getCost();
    }

    private void setRemainingBalance(Cash cash){
        this.remainingBalance = cash;

    }

    public void transferCashToATM(Cash inputCash) throws CashException {
        try {
            transaction(inputCash, getRemainingBalance(), inputCash.getCost());
        } catch (CashException ex) {
            ex.printStackTrace();
            throw new CashException("Can not take this money");
        }
    }


    public Cash withdrawal(long cost) throws CashException {
        if(cost > remainingBalance.getCost()){
            throw new CashException("ATM has not enough money");
        }
        Cash differenceInCash = this.getRemainingBalance().makeChangeCostToBanknotesFromCurrentCash(cost);
        Cash outputCash = new Cash();
        transaction(this.remainingBalance, outputCash, cost);
        return differenceInCash;
    }

    private static void transaction(Cash sourceOfCash, Cash cashRecipient, long cost) throws CashException{
        Cash tempSourceOfCash = new Cash(sourceOfCash);
        Cash tempOutputCash = new Cash(cashRecipient);
        try {
            Cash outputCash = sourceOfCash.makeChangeCostToBanknotesFromCurrentCash(cost);
            for (Banknote b : outputCash.getBanknotes().keySet()) {
                int numberOfBanknotes = outputCash.getBanknotes().get(b);
                while (numberOfBanknotes > 0) {
                    tempSourceOfCash.takeOutABanknote(b);
                    tempOutputCash.addBanknote(b);
                    numberOfBanknotes--;
                }
            }
            sourceOfCash.setBanknotes(tempSourceOfCash.getBanknotes());
            cashRecipient.setBanknotes(tempOutputCash.getBanknotes());
        } catch (CashException ex) {
            throw ex;
        }catch(Exception ex){
            System.out.println("Transaction was failed");
            ex.printStackTrace();
            throw ex;
        }
    }

    public void saveATMState(){
        savedState = new ATMState(remainingBalance);
    }

    public void restore(){
        if(savedState != null) {
            savedState.restore();
        }else{
            setRemainingBalance(new Cash());
        }
    }

    private class ATMState{
        private final Cash savedBalance;

        private ATMState(Cash remainingBalance){
            this.savedBalance = new Cash(remainingBalance);
        }

        private void restore(){
            remainingBalance = savedBalance;
        }

    }

}
