package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toGenerator.MsgAnswerFromDBService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgFindById extends MsgToDB {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private long id;
    public MsgFindById(Address from, Address to,long id){
        super(from, to);
        this.id = id;
    }

    @Override
    public <T> void exec(DBService dbService) {
        try {
            T object = (T) dbService.findById(id);
            Message answer = new MsgAnswerFromDBService(getTo(), getFrom(), object.toString());

            dbService.getMS().sendMessage(answer);
        }catch(DBException e){
            logger.log(Level.SEVERE, "Can not get query to database", e);
        }
    }
}
