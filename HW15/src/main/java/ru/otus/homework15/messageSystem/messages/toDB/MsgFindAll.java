package ru.otus.homework15.messageSystem.messages.toDB;

import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toDB.MsgToDB;
import ru.otus.homework15.messageSystem.messages.toGenerator.MsgAnswerFromDBService;
import ru.otus.homework15.utils.ListUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgFindAll extends MsgToDB {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public MsgFindAll(Address from, Address to){
        super(from, to);
    }

    @Override
    public <T> void exec(DBService dbService) {
        try {
            List<T> resultList = dbService.findAll();
            Message answer = new MsgAnswerFromDBService(getTo(), getFrom(), ListUtils.listToHtmlString(resultList));
            dbService.getMS().sendMessage(answer);
        }catch(DBException e){
            logger.log(Level.SEVERE, "Can not get query to database", e);
        }
    }
}
