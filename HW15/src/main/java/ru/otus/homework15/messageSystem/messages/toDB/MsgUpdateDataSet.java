package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.utils.ReflectionUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgUpdateDataSet extends MsgToDB {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private long id;
    private String newStringValue;

    public MsgUpdateDataSet(Address from, Address to, Long id, String newStringValue){
        super(from, to);
        this.id = id;
        this.newStringValue = newStringValue;
    }

    @Override
    public <T> void exec(DBService dbService) {
        try {
            Object object = dbService.findById(id);
            ReflectionUtils.changeSomeStringField(object, newStringValue);
            dbService.update((UserDataSet) object);
        }catch(Exception e){
            logger.log(Level.SEVERE, "Can not update object", e);
        }
    }
}
