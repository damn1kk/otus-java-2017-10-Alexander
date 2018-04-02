package ru.otus.homework16;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.msg.AnswerRegisterMsg;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.forDBService.AnswerFindPassByLoginMsg;
import ru.otus.homework16.msg.forDBService.AnswerRegisterNewUserMsg;
import ru.otus.homework16.msg.forDBService.FindPassByLoginMsg;
import ru.otus.homework16.msg.forDBService.RegisterNewUserMsg;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.*;
import javax.websocket.server.ServerContainer;
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

    private final static String MAIN_PAGE_TEMPLATE = "main_page.html";
    private final static String SUCCESSFUL_REGISTRATION_TEMPLATE = "successful_registration.html";
    private final static String REGISTRATION_FAILED_TEMPLATE = "registration_failed.html";
    private final static String SUCCESSFUL_LOGIN_TEMPLATE = "successful_login.html";
    private final static String LOGIN_FAILED_TEMPLATE = "login_failed.html";

    private Set<Session> clients = new HashSet<>();
    private JSONParser jsonParser = new JSONParser();

    public FrontendServiceMsgClient(){
        super(TypeOfAddressee.FRONT_SERVICE);
        connectToServerBySocket(HOST,PORT);
    }

    @OnOpen
    public void onOpen(Session session){
        logger.info("connected..." + session.getId());
        clients.add(session);
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
                sendMessageToWebSocketClient(createMessageMainPage());
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

    private JSONObject createMessageMainPage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(MAIN_PAGE_TEMPLATE, null));
        return jsonObject;
    }

    private JSONObject createMessageSuccessfulRegistration(String login){
        Map<String, Object> variables = new HashMap<>();
        variables.put("login", login);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        HtmlPageGenerator generator = HtmlPageGenerator.instance();
        jsonObject.put("htmlBody", generator.generatePage(SUCCESSFUL_REGISTRATION_TEMPLATE, variables));

        return jsonObject;
    }

    private JSONObject createMessageRegistrationFailed(String login, String reason){
        Map<String, Object> variables = new HashMap<>();
        variables.put("reason", reason);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(REGISTRATION_FAILED_TEMPLATE, variables));
        return jsonObject;
    }

    private JSONObject createMessageSuccessfulLogin(String login){
        Map<String, Object> variables = new HashMap<>();
        variables.put("login", login);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(SUCCESSFUL_LOGIN_TEMPLATE, variables));
        return jsonObject;
    }

    private JSONObject createMessageLoginFailed(String reason){
        Map<String, Object> variables = new HashMap<>();
        variables.put("reason", reason);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "changeBody");
        jsonObject.put("htmlBody", HtmlPageGenerator.instance().generatePage(LOGIN_FAILED_TEMPLATE, variables));
        return jsonObject;
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
