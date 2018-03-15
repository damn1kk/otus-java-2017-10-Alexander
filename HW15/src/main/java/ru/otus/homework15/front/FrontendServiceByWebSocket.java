package ru.otus.homework15.front;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.MessageSystem;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toCacheService.MsgAddCacheHitMissListener;
import ru.otus.homework15.messageSystem.messages.toCacheService.MsgDeleteCacheHitMissListener;
import ru.otus.homework15.messageSystem.messages.toDB.MsgAddQueryListener;
import ru.otus.homework15.messageSystem.messages.toDB.MsgDeleteQueryListener;
import ru.otus.homework15.messageSystem.messages.toGenerator.*;
import ru.otus.homework15.queryGenerator.GeneratorStatusListener;
import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.dbService.QueryListener;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/wsserver", configurator = SpringConfigurator.class)
public class FrontendServiceByWebSocket implements FrontendService, CacheHitMissListener, QueryListener, GeneratorStatusListener{
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final Address address;
    private final MessageSystemContext messageSystemContext;
    private int lastCacheHit = 0;
    private int lastCacheMiss = 0;
    private boolean lastStatusOfGenerator = false;

    private Set<Session> clients = new HashSet<>();
    private JSONParser jsonParser = new JSONParser();

    private FrontendServiceByWebSocket(Address address, MessageSystemContext messageSystemContext){
        this.address = address;
        this.messageSystemContext = messageSystemContext;

        this.messageSystemContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }

    @OnOpen
    public void onOpen(Session session){
        logger.info("Connected...." + session.getId());
        clients.add(session);

        sendInitInfoToClient(session);

        if(!getMS().isStarted()) {
            getMS().start();

            Message messageToCacheService = new MsgAddCacheHitMissListener(
                    getAddress(), messageSystemContext.getDbAddress(), this
            );
            getMS().sendMessage(messageToCacheService);

            Message messageToDBService = new MsgAddQueryListener(
                    getAddress(), messageSystemContext.getDbAddress(), this
            );
            getMS().sendMessage(messageToDBService);

            Message messageToGenerator = new MsgAddGeneratorStatusListener(
                    getAddress(), messageSystemContext.getQueryGeneratorAddress(), this
            );
            getMS().sendMessage(messageToGenerator);
        }
    }

    @OnMessage
    public void onMessage(Session session, String messageFromUser){
        logger.info("Receive : " + messageFromUser + "\nFROM: " + session.getId());
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(messageFromUser);
            String commandToGenerator = (String) jsonObject.get("commandToGenerator");

            if(commandToGenerator.equals("start")){
                Message messageToGenerator = new MsgStart(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }else if(commandToGenerator.equals("stop")){
                Message messageToGenerator = new MsgStop(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);

            }else if(commandToGenerator.equals("findAll")){
                Message messageToGenerator = new MsgFindAll(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }else if(commandToGenerator.equals("findByRandomId")){
                Message messageToGenerator = new MsgFindByRandomId(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }else if(commandToGenerator.equals("saveNewDataSet")){
                Message messageToGenerator = new MsgSaveNewRandomDataSet(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }else if(commandToGenerator.equals("updateRandomDataSet")){
                Message messageToGenerator = new MsgUpdateRandomDataSet(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }else if(commandToGenerator.equals("deleteByRandomId")){
                Message messageToGenerator = new MsgDeleteByRandomId(getAddress(), messageSystemContext.getQueryGeneratorAddress());
                getMS().sendMessage(messageToGenerator);
            }
        }catch(ParseException e){
            logger.log(Level.SEVERE, "Can not parse json from message", e);
        }
    }

    @OnClose
    public void onClose(Session session){
        logger.info("Closed");
        clients.remove(session);


        if(clients.isEmpty()) {
            Message messageStopGenerator = new MsgStop(getAddress(), messageSystemContext.getQueryGeneratorAddress());
            getMS().sendMessage(messageStopGenerator);

            Message messageToCacheService = new MsgDeleteCacheHitMissListener(
                    getAddress(), messageSystemContext.getDbAddress(), this
            );
            getMS().sendMessage(messageToCacheService);

            Message messageToDbService = new MsgDeleteQueryListener(
                    getAddress(), messageSystemContext.getDbAddress(), this
            );
            getMS().sendMessage(messageToDbService);

            Message messageToGenerator = new MsgDeleteGeneratorStatusListener(
                    getAddress(), messageSystemContext.getQueryGeneratorAddress(), this
            );
            getMS().sendMessage(messageToGenerator);

            getMS().dispose();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.warning("Error from " + session.getId() + " ---- " + throwable.getMessage());
    }

    private JSONObject createMessageCacheStatistic(int cacheHit, int cacheMiss){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "statisticMessage");
        jsonObject.put("cacheHit", cacheHit);
        jsonObject.put("cacheMiss", cacheMiss);
        return jsonObject;
    }

    private JSONObject createMessageStatusOfGenerator(boolean isStarted){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "statusMessage");
        if(isStarted){
            jsonObject.put("started", "true");
        }else{
            jsonObject.put("started", "false");
        }
        return jsonObject;
    }

    private JSONObject createMessageNewQuery(String query){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "newQuery");
        jsonObject.put("query", query);
        return jsonObject;
    }

    private JSONObject createMessageQueryStringResult(String result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "queryResult");
        jsonObject.put("queryResult", result);
        return jsonObject;
    }

    private void sendInitInfoToClient(Session session){
        JSONObject statusOfGenerator = createMessageStatusOfGenerator(lastStatusOfGenerator);
        JSONObject cacheStatistic = createMessageCacheStatistic(lastCacheHit, lastCacheMiss);

        sendMessageToClient(session, statusOfGenerator);
        sendMessageToClient(session, cacheStatistic);
    }

    private void sendMessageToClient(Session session, JSONObject jsonObject){
        try {
            session.getBasicRemote().sendText(jsonObject.toJSONString());
        }catch(IOException e){
            logger.log(Level.SEVERE, "Can not write json into message", e);
        }
    }

    private void sendMessageToAllClients(JSONObject jsonObject){
        try {
            for (Session s : clients) {
                s.getBasicRemote().sendText(jsonObject.toJSONString());
                logger.info("send to : " + s.getId() + "\n message: " + jsonObject.toJSONString());
            }
        }catch(IOException e){
            logger.log(Level.SEVERE, "Can not write json into message", e);
        }
    }

    private void sendMessageToAllClientsExceptMyself(Session mySession, JSONObject jsonObject){
        try {
            for (Session s : clients) {
                if(!s.equals(mySession)) {
                    s.getBasicRemote().sendText(jsonObject.toJSONString());
                    logger.info("send to : " + s.getId() + "\n message: " + jsonObject.toJSONString());
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE, "Can not write json into message", e);
        }
    }

    private void sendMessageRefreshCacheStat(int cacheHit, int cacheMiss){
        sendMessageToAllClients(createMessageCacheStatistic(cacheHit, cacheMiss));
    }

    private void sendMessageChangeStatusOfGenerator(boolean isStarted){
        sendMessageToAllClients(createMessageStatusOfGenerator(isStarted));
    }

    @Override
    public void cacheHitMissChanged(int cacheHit, int cacheMiss) {
        lastCacheHit = cacheHit;
        lastCacheMiss = cacheMiss;

        sendMessageRefreshCacheStat(cacheHit, cacheMiss);
    }

    @Override
    public void getNewQuery(String query) {
        sendMessageToAllClients(createMessageNewQuery(query));
    }

    @Override
    public void sendQueryStringResultToClients(String result){
        sendMessageToAllClients(createMessageQueryStringResult(result));
    }

    @Override
    public void generatorStatusChanged(boolean isStarted){
        lastStatusOfGenerator = isStarted;

        sendMessageChangeStatusOfGenerator(isStarted);
    }
}
