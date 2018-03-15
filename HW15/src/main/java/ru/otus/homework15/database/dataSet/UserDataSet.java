package ru.otus.homework15.database.dataSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<PhoneDataSet> phones = new ArrayList<>();

    private UserDataSet(){}

    public UserDataSet(long id, String name, int age){
        super(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age){
        super(-1L);
        this.name = name;
        this.age = age;
    }

    public void addPhone(PhoneDataSet phone){
        this.phones.add(phone);
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                ", id=" + id +
                '}';
    }
}
