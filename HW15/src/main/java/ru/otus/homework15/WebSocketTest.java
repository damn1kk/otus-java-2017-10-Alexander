package ru.otus.homework15;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import ru.otus.homework15.database.GeneratorStatusListener;
import ru.otus.homework15.database.QueryGenerator;
import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.database.dbService.QueryResultListener;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/wstest", configurator = SpringConfigurator.class)
public class WebSocketTest implements CacheHitMissListener, QueryListener, QueryResultListener, GeneratorStatusListener{
    Logger logger = Logger.getLogger(this.getClass().getName());

    private Set<Session> clients = new HashSet<>();
    private JSONParser jsonParser = new JSONParser();

    @Autowired
    private QueryGenerator queryGenerator;

    private WebSocketTest(QueryGenerator queryGenerator){
        this.queryGenerator = queryGenerator;
        queryGenerator.addCacheHitMissListener(this);
        queryGenerator.addQueryListener(this);
        queryGenerator.addQueryResultListener(this);
        queryGenerator.addGeneratorStatusListener(this);
    }

    @OnOpen
    public void onOpen(Session session){
        logger.info("Connected...." + session.getId());
        clients.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message){
        logger.info("Receive : " + message + "\nFROM: " + session.getId());
        String commandToGenerator = "";
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(message);
            commandToGenerator = (String) jsonObject.get("commandToGenerator");
            if(commandToGenerator.equals("start")){
                if (!queryGenerator.isStarted()) {
                    queryGenerator.start();
                    logger.info("Query generator launched");
                }
            }else if(commandToGenerator.equals("stop")){
                queryGenerator.stop();
                logger.info("Query generator stopped");
            }else if(commandToGenerator.equals("findAll")){
                queryGenerator.doFindAllQuery();
            }else if(commandToGenerator.equals("findByRandomId")){
                queryGenerator.doFindByRandomIdQuery();
            }else if(commandToGenerator.equals("saveNewDataSet")){
                queryGenerator.doSaveNewDataSetQuery();
            }else if(commandToGenerator.equals("updateRandomDataSet")){
                queryGenerator.doUpdateDataSetQuery();
            }else if(commandToGenerator.equals("deleteByRandomId")){
                queryGenerator.doDeleteByRandomIdQuery();
            }
        }catch(ParseException e){
            logger.log(Level.SEVERE, "Can not parse json from message", e);
        }
    }

    @OnClose
    public void onClose(Session session){
        logger.info("Closed");
        clients.remove(session);

        if(clients.isEmpty())
            queryGenerator.stop();
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.warning("Error from " + session.getId() + " ---- " + throwable.getMessage());
    }

    private JSONObject createMessageRefreshCacheStat(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "statisticMessage");
        jsonObject.put("cacheHit", queryGenerator.getCacheHit());
        jsonObject.put("cacheMiss", queryGenerator.getCacheMiss());
        return jsonObject;
    }

    private JSONObject createMessageStatusOfGenerator(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "statusMessage");
        if(queryGenerator.isStarted()){
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

    private JSONObject createMessageQueryResult(String result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfMessage", "queryResult");
        jsonObject.put("queryResult", result);
        return jsonObject;
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

    private void sendMessageRefreshCacheStat(){
        sendMessageToAllClients(createMessageRefreshCacheStat());
    }

    private void sendMessageChangeStatusOfGenerator(){
        sendMessageToAllClients(createMessageStatusOfGenerator());
    }

    @Override
    public void cacheHitMissChanged(int cacheHit, int cacheMiss) {
        sendMessageRefreshCacheStat();
    }

    @Override
    public void getNewQuery(String query) {
        sendMessageToAllClients(createMessageNewQuery(query));
    }

    @Override
    public void getQueryResult(String result){
        sendMessageToAllClients(createMessageQueryResult(result));
    }

    @Override
    public void generatorStatusChanged(){
        sendMessageChangeStatusOfGenerator();
    }
}
