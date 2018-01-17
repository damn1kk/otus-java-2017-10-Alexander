package ru.otus.homework11.dataSet;

import javax.persistence.*;

@Entity
@Table(name = "PHONE")
public class PhoneDataSet {

    @Id
    @Column(name = "number", unique = true)
    private String number;

    public PhoneDataSet(){}

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
