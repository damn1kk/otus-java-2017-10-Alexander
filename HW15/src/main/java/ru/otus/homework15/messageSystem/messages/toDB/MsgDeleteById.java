package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.messageSystem.Address;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgDeleteById extends MsgToDB {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private long id;
    public MsgDeleteById(Address from, Address to, long id){
        super(from, to);
        this.id = id;
    }

    @Override
    public <T> void exec(DBService dbService) {
        try {
            dbService.deleteById(id);
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }
}
