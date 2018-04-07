package ru.otus.homework16.frontService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.homework16.ClientMsgSystem;
import ru.otus.homework16.HtmlPageGenerator;
import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.msg.AllDbServicesMsg;
import ru.otus.homework16.msg.AnswerRegisterMsg;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.forDBService.AnswerFindPassByLoginMsg;
import ru.otus.homework16.msg.forDBService.AnswerRegisterNewUserMsg;
import ru.otus.homework16.msg.forDBService.FindPassByLoginMsg;
import ru.otus.homework16.msg.forDBService.RegisterNewUserMsg;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


@ServerEndpoint(value = "/wsserver", configurator = MyConfigurator.class)
public class FrontendServiceMsgClient extends ClientMsgSystem {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final static String DBSERVICE_NAME = "DBService";
    private final static String HOST = "localhost";
    private final static int PORT = 9090;
    private final MessageGeneratorToBrowser generatorToBrowser = new MessageGeneratorToBrowser();

    private Set<Session> clients = new HashSet<>();
    private Set<String> allDbServices = new HashSet<>();
    private JSONParser jsonParser = new JSONParser();

    public FrontendServiceMsgClient(){
        super(TypeOfAddressee.FRONT_SERVICE);
        connectToServerBySocket(HOST,PORT);
    }

    @OnOpen
    public void onOpen(Session session){
        logger.info("connected..." + session.getId());
        clients.add(session);

        JSONObject message = generatorToBrowser.createMessageUpdateDbServices(this.allDbServices);
        try {
            session.getBasicRemote().sendText(message.toJSONString());
        }catch(IOException e){
            logger.log(Level.SEVERE,"Can not write json into message", e);
        }
    }

    @OnMessage
    public void onMessage(Session session, String messageFromUser){
        logger.info("Receive: " + messageFromUser + "\nFrom: " + session.getId());
        try{
            JSONObject jsonObject = (JSONObject) jsonParser.parse(messageFromUser);
            String typeOfMessage = (String) jsonObject.get("typeOfMessage");

            if(typeOfMessage.equals("signIn")){
                String login = (String) jsonObject.get("login");
                String password = (String) jsonObject.get("password");
                Msg msgToDb = new FindPassByLoginMsg(this.getMsId(), DBSERVICE_NAME, login, password);
                try {
                    sendMessage(msgToDb);
                }catch(Exception e){
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }else if(typeOfMessage.equals("signUp")){
                String login = (String) jsonObject.get("login");
                String password = (String) jsonObject.get("password");
                Msg msgToDb = new RegisterNewUserMsg(this.getMsId(), DBSERVICE_NAME, login, password);
                try{
                    sendMessage(msgToDb);
                }catch(Exception e){
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }else if(typeOfMessage.equals("backToMainPage")){
                sendMessageToWebSocketClient(generatorToBrowser.createMessageMainPage());
            }

        }catch(ParseException e){
            logger.log(Level.SEVERE, "Can not parse json from message", e);
        }
    }

    @OnClose
    public void onClose(Session session){
        logger.info("Disconnected: " + session.getId());
        clients.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.warning("Error from " + session.getId() + " ---- " + throwable.getMessage());
    }

    private void sendMessageToWebSocketClient(JSONObject jsonObject){
        try {
            for (Session s : clients) {
                s.getBasicRemote().sendText(jsonObject.toJSONString());
                logger.info("send to : " + s.getId() + "\n message: " + jsonObject.toJSONString());
            }
        }catch(IOException e){
            logger.log(Level.SEVERE, "Can not write json into message", e);
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
                        JSONObject message = generatorToBrowser.createMessageSuccessfulLogin(login);
                        sendMessageToWebSocketClient(message);
                    }else{
                        JSONObject message = generatorToBrowser.createMessageLoginFailed(((AnswerFindPassByLoginMsg) msg).getReasonOfError());
                        sendMessageToWebSocketClient(message);
                    }
                    if(password != null && password.length() != 0){
                    }
                }else if(msg instanceof AnswerRegisterNewUserMsg){
                    String login = ((AnswerRegisterNewUserMsg) msg).getLogin();
                    boolean isSuccess = ((AnswerRegisterNewUserMsg) msg).isSuccess();
                    if(isSuccess){
                        JSONObject message = generatorToBrowser.createMessageSuccessfulRegistration(login);
                        sendMessageToWebSocketClient(message);
                    }else{
                        JSONObject message = generatorToBrowser.createMessageRegistrationFailed(login, ((AnswerRegisterNewUserMsg) msg).getReasonOfError());
                        sendMessageToWebSocketClient(message);
                    }
                }else if(msg instanceof AllDbServicesMsg){
                    Set<String> allDbServices = ((AllDbServicesMsg) msg).getDbServices();
                    this.allDbServices = allDbServices;
                    JSONObject message = generatorToBrowser.createMessageUpdateDbServices(allDbServices);
                    sendMessageToWebSocketClient(message);
                }
            }
        }catch(InterruptedException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
