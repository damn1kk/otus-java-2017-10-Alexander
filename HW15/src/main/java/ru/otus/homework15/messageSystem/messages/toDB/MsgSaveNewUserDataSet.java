package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.messages.toGenerator.MsgAnswerFromDBService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgSaveNewUserDataSet extends MsgToDB {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Object object;

    public MsgSaveNewUserDataSet(Address from, Address to, Object object){
        super(from, to);
        this.object = object;
    }

    @Override
    public <T> void exec(DBService dbService) {
        try {
            dbService.save((UserDataSet) object);
        }catch(DBException e){
            logger.log(Level.SEVERE, "Can not save new object", e);
        }
    }
}
