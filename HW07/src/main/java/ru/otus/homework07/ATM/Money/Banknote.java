package ru.otus.homework07.ATM.Money;

public class Banknote implements Comparable {
    private final Denomination denomination;
    private final int cost;

    public Banknote(Denomination denomination){
        this.denomination = denomination;
        this.cost = denomination.getCost();
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getCost(){
        return cost;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 37 + cost * 17;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj instanceof Banknote){
            if(this.getDenomination() == ((Banknote) obj).getDenomination()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Denomination: " + denomination + "(" + cost + ")";
    }

    @Override
    public int compareTo(Object o) {
        if(this.equals(o)) return 0;
        if(this.getCost() < ((Banknote)o).getCost()) return -1;
        else if(this.getCost() == ((Banknote)o).getCost()) return 0;
        else return 1;
    }
}
