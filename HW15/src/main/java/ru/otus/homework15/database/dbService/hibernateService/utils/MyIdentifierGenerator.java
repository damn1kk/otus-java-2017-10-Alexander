package ru.otus.homework15.database.dbService.hibernateService.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import ru.otus.homework15.database.dataSet.DataSet;
import ru.otus.homework15.database.dataSet.UserDataSet;

import java.io.Serializable;

public class MyIdentifierGenerator extends IdentityGenerator  {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if(object instanceof DataSet){
            DataSet user = (DataSet) object;
            if(user.getId() > 0) {
                return user.getId();
            }else{
                return super.generate(session, object);
            }
        }else{
            return super.generate(session, object);
        }
    }
}

