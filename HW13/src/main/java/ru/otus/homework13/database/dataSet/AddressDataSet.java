package ru.otus.homework13.database.dataSet;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS", uniqueConstraints = @UniqueConstraint(columnNames = {"city", "street"}))
public class AddressDataSet extends DataSet{

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    public AddressDataSet(){}

    public AddressDataSet(String city, String street) {
        this.street = street;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "addressId=" + super.getId() +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
