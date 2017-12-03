package ru.otus.homework06.ATM.Money;


public enum Denomination {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private int cost;

    Denomination(int cost){
        this.cost = cost;
    }

    /*private static Map<Denomination, Integer> map = new HashMap<>();

    static{
        for(Denomination denomination : Denomination.values()){
            map.put(denomination, denomination.cost);
        }
    }*/

    public int getCost(){
        return cost;
    }


}
