package ru.otus.homework10.dataSet;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public abstract class DataSet {
    @Id
    @GeneratedValue(generator = "MyGenerator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "MyGenerator",
            strategy = "ru.otus.homework10.dbService.hibernateService.utils.MyIdentifierGenerator")
    @Column(name = "id")
    protected long id;

    public DataSet(){}

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
