package ru.otus.homework09.dataSet;

public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
