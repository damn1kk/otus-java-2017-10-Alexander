package ru.otus.homework16;

import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.database.dataSet.PasswordDataSet;
import ru.otus.homework16.database.dbService.DBException;
import ru.otus.homework16.database.dbService.hibernateService.HibernatePasswordDBService;
import ru.otus.homework16.msg.AnswerRegisterMsg;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.forDBService.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServiceMsgClient extends ClientMsgSystem{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private HibernatePasswordDBService dbService = new HibernatePasswordDBService();

    public DBServiceMsgClient(){
        super(TypeOfAddressee.DB_SERVICE);
    }

    public DBServiceMsgClient(String msId){
        super(msId, TypeOfAddressee.DB_SERVICE);
    }

    @Override
    protected void handleMsg(){
        try {
            while (true) {
                Msg msg = msgWorker.take();
                logger.info("Message received: " + msg.toString());

                if(msg instanceof  AnswerRegisterMsg){
                    setMsId(msg.getTo());
                    isRegistered = true;

                }else if(msg instanceof FindPassByLoginMsg){
                    Msg answer = null;
                    try {
                        String login = ((FindPassByLoginMsg) msg).getLogin();
                        String passwordFromUser = ((FindPassByLoginMsg) msg).getPassword();

                        if(login != null && login.length() != 0 &&
                                passwordFromUser != null && passwordFromUser.length() != 0){

                            PasswordDataSet passwordDataSet = dbService.findPasswordByLogin(login);
                            if(passwordDataSet == null){
                                answer = new AnswerFindPassByLoginMsg(
                                        getMsId(),
                                        msg.getFrom(),
                                        login,
                                        passwordFromUser,
                                        false,
                                        "Wrong login"
                                );
                            }else{
                                String passwordFromDB = passwordDataSet.getPassword();
                                if (passwordFromUser.equals(passwordFromDB)) {
                                    answer = new AnswerFindPassByLoginMsg(
                                            getMsId(),
                                            msg.getFrom(),
                                            login,
                                            passwordFromDB,
                                            true,
                                            ""
                                    );
                                }else{
                                    answer = new AnswerFindPassByLoginMsg(
                                            getMsId(),
                                            msg.getFrom(),
                                            login,
                                            passwordFromUser,
                                            false,
                                            "Wrong password"
                                    );
                                }
                            }
                        }else{
                            answer = new AnswerFindPassByLoginMsg(
                                    getMsId(),
                                    msg.getFrom(),
                                    login,
                                    passwordFromUser,
                                    false,
                                    "Empty login or password"
                            );
                        }
                    }catch(DBException e){
                        logger.log(Level.SEVERE, e.getMessage());
                        answer = new ProblemWithDataBaseMsg(getMsId(), msg.getFrom(), e.getMessage());
                    }finally{
                        try {
                            if(answer != null) {
                                sendMessage(answer);
                            }
                        }catch(Exception ex){
                            logger.log(Level.SEVERE, ex.getMessage());
                        }
                    }
                }else if(msg instanceof RegisterNewUserMsg){
                    Msg answer = null;

                    try {
                        String login = ((RegisterNewUserMsg) msg).getLogin();
                        String passwordFromUser = ((RegisterNewUserMsg) msg).getPassword();

                        if(login != null && login.length() != 0 &&
                                passwordFromUser != null && passwordFromUser.length() != 0){

                            PasswordDataSet passwordDataSet = dbService.findPasswordByLogin(login);

                            if (passwordDataSet != null) {
                                answer = new AnswerRegisterNewUserMsg(
                                        getMsId(),
                                        msg.getFrom(),
                                        passwordDataSet.getLogin(),
                                        passwordDataSet.getPassword(),
                                        false,
                                        "This login is already taken"
                                );
                            } else {
                                dbService.save(new PasswordDataSet(login, passwordFromUser));
                                answer = new AnswerRegisterNewUserMsg(
                                        getMsId(),
                                        msg.getFrom(),
                                        login,
                                        passwordFromUser,
                                        true,
                                        ""
                                );
                            }
                        }else{
                            answer = new AnswerRegisterNewUserMsg(
                                    getMsId(),
                                    msg.getFrom(),
                                    login,
                                    passwordFromUser,
                                    false,
                                    "Empty login or password"
                            );
                        }
                    } catch (DBException e) {
                        logger.log(Level.SEVERE, e.getMessage());
                        answer = new ProblemWithDataBaseMsg(getMsId(), msg.getFrom(), e.getMessage());
                    } finally {
                        try {
                            if (answer != null) {
                                sendMessage(answer);
                            }
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, ex.getMessage());
                        }
                    }

                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public TypeOfAddressee getTypeMS() {
        return TypeOfAddressee.DB_SERVICE;
    }
}
