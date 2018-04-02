package ru.otus.homework16.database.dataSet;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class DataSet<ID extends Serializable> {
    @Id
    @GeneratedValue(generator = "MyGenerator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "MyGenerator",
            strategy = "ru.otus.homework16.database.dbService.hibernateService.utils.MyIdentifierGenerator")
    @Column(name = "id")
    protected Long id;

    public DataSet(){}

    public DataSet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
