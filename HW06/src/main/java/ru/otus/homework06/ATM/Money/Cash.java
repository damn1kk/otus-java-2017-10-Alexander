package ru.otus.homework06.ATM.Money;

import java.util.*;

public class Cash {
    private Map<Banknote, Integer> banknotes;
    private long cost;

    public Cash(){
        banknotes = new HashMap<>();
        cost = 0;
    }

    public Cash(long cost) throws CashException {
        Cash tempCash = new Cash(makeChangeCostToBanknotesForPerfectChange(cost));
        this.banknotes = tempCash.getBanknotes();
        this.cost = tempCash.getCost();
    }

    public Cash(Cash otherCash){
        banknotes = new HashMap<>(otherCash.getBanknotes());
        cost = otherCash.getCost();
    }

    public Map<Banknote, Integer> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Map<Banknote, Integer> banknotes){
        this.banknotes = banknotes;
        long cost = 0;
        for(Banknote banknote : banknotes.keySet()){
            cost += banknote.getCost() * banknotes.get(banknote);
        }
        this.cost = cost;
    }

    public long getCost(){
        return cost;
    }

    private void setCost(long cost){
        this.cost = cost;
    }

    public void addBanknote(Banknote inputBanknote){
        if(getBanknotes().containsKey(inputBanknote)) {
            int count = getBanknotes().get(inputBanknote);
            getBanknotes().put(inputBanknote, ++count);
            this.setCost(this.getCost() + inputBanknote.getCost());
        }else{
            this.getBanknotes().put(inputBanknote, 1);
            this.setCost(this.getCost() + inputBanknote.getCost());
        }
    }

    public void takeOutABanknote(Banknote outBanknote) throws CashException{
        if (this.hasBanknote(outBanknote)) {
            int count = this.getBanknotes().get(outBanknote);
            if(count > 1) {
                this.getBanknotes().put(outBanknote, --count);
            }else{
                this.getBanknotes().remove(outBanknote);
            }
            this.setCost(this.getCost() - outBanknote.getCost());
        } else {
            throw new CashException("have not this banknote");
        }
    }

    public boolean hasBanknote(Banknote banknote){
        Set<Banknote> banknoteSet = getBanknotes().keySet();
        for(Banknote b : banknoteSet){
            if(b.equals(banknote) && (this.getBanknotes().get(b) > 0)){
                    return true;
                }
            }
        return false;
    }

    public Cash makeChangeCostToBanknotesForPerfectChange(long cost) throws CashException {
        List<Banknote> allBanknotes = new ArrayList<>();
        for (Denomination d : Denomination.values()) {
            allBanknotes.add(new Banknote(d));
        }
        allBanknotes.sort((a, b) -> b.compareTo(a));

        Cash resultCash = new Cash();

        for (Banknote b : allBanknotes) {
            while (b.getCost() <= cost) {
                resultCash.addBanknote(b);
                cost -= b.getCost();
            }
        }

        if (cost == 0) {
            return resultCash;
        } else {
            throw new CashException("can not make change this cash");
        }
    }

    public Cash makeChangeCostToBanknotesFromCash(long cost, Cash cash) throws CashException{
        TreeSet<Banknote> reverseSortedBanknotes = new TreeSet<>(Collections.reverseOrder());
        reverseSortedBanknotes.addAll(cash.getBanknotes().keySet());
        Map<Banknote, Integer> banknotes = new HashMap<>(cash.getBanknotes());
        Cash resultCash = new Cash();

        for (Banknote b : reverseSortedBanknotes) {
            int numberOfBanknotes = banknotes.get(b);
            while (b.getCost() <= cost) {
                if (numberOfBanknotes > 0) {
                    resultCash.addBanknote(b);
                    cost -= b.getCost();
                    banknotes.put(b, --numberOfBanknotes);
                } else {
                    break;
                }
            }
        }

        if (cost == 0) {
            return resultCash;
        } else {
            throw new CashException("can not make change this cash");
        }
    }

    public Cash makeChangeCostToBanknotesFromCurrentCash(long cost) throws CashException {
        return makeChangeCostToBanknotesFromCash(cost, this);
    }

    @Override
    public String toString() {
        TreeMap<Banknote, Integer> sortedBanknotes = new TreeMap<>(Collections.reverseOrder());
        sortedBanknotes.putAll(this.getBanknotes());
        return "cost: " + cost + "\n banknotes: \n" + sortedBanknotes;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.getCost() == ((Cash)obj).getCost()){
            if(this.getBanknotes().equals((Cash)obj)){
                return true;
            }
        }
        return false;
    }
}
