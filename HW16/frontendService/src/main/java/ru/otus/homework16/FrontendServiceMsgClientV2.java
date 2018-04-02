/*
package ru.otus.homework16;

import org.json.simple.JSONObject;
import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.msg.AnswerRegisterMsg;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.forDBService.AnswerFindPassByLoginMsg;
import ru.otus.homework16.msg.forDBService.AnswerRegisterNewUserMsg;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendServiceMsgClientV2 extends ClientMsgSystem {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final static String DBSERVICE_NAME = "DBService";
    private final static String HOST = "localhost";
    private final static int PORT = 9090;

    public FrontendServiceMsgClientV2(){
        super(TypeOfAddressee.FRONT_SERVICE);
        if(!isRegistered){
            connectToServerBySocket(HOST, PORT);
        }
    }

    @Override
    protected void handleMsg() {
        try {
            while (true) {
                Msg msg = msgWorker.take();
                logger.info("Message received: " + msg.toString());
                if(msg instanceof AnswerRegisterMsg){
                    setMsId(msg.getTo());
                    isRegistered = true;
                    logger.info("It was registered as: " + msg.getTo());
                }

                if(msg instanceof AnswerFindPassByLoginMsg){
                    String password = ((AnswerFindPassByLoginMsg) msg).getPassword();
                    String login = ((AnswerFindPassByLoginMsg) msg).getLogin();
                    boolean isSuccess = ((AnswerFindPassByLoginMsg) msg).isSuccess();
                    if(isSuccess){
                        JSONObject message = createMessageSuccessfulLogin(login);
                        sendMessageToWebSocketClient(message);
                    }else{
                        JSONObject message = createMessageLoginFailed(((AnswerFindPassByLoginMsg) msg).getReasonOfError());
                        sendMessageToWebSocketClient(message);
                    }
                    if(password != null && password.length() != 0){
                    }
                }else if(msg instanceof AnswerRegisterNewUserMsg){
                    String login = ((AnswerRegisterNewUserMsg) msg).getLogin();
                    boolean isSuccess = ((AnswerRegisterNewUserMsg) msg).isSuccess();
                    if(isSuccess){
                        JSONObject message = createMessageSuccessfulRegistration(login);
                        sendMessageToWebSocketClient(message);
                    }else{
                        JSONObject message = createMessageRegistrationFailed(login, ((AnswerRegisterNewUserMsg) msg).getReasonOfError());
                        sendMessageToWebSocketClient(message);
                    }
                }
            }
        }catch(InterruptedException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public TypeOfAddressee getTypeMS() {
        return TypeOfAddressee.FRONT_SERVICE;
    }
}
*/
