package ru.otus.homework15.database;

import ru.otus.homework15.database.cach.CacheHitMissListener;
import ru.otus.homework15.database.dataSet.UserDataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.QueryListener;
import ru.otus.homework15.database.dbService.QueryResultListener;
import ru.otus.homework15.database.dbService.hibernateService.HibernateCachedUserService;

import javax.websocket.Session;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryGenerator{
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Set<GeneratorStatusListener> statusListenerSet = new HashSet<>();
    private static  volatile boolean stopFlag = false;
    private static  volatile boolean isStarted = false;
    private List<Integer> usedId = new LinkedList<>();
    private Random random = new Random();

    HibernateCachedUserService dbService;

    public QueryGenerator(HibernateCachedUserService dbService){
        this.dbService = dbService;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start(){
        stopFlag = false;
        isStarted = true;

        notifyStatusListeners();

        new Thread(()->{
            while(!stopFlag) {
                try {
                    doRandomQuery(usedId);
                    try {
                        Thread.sleep(1000);
                    }catch(InterruptedException ex){
                    }
                } catch (DBException e) {
                    logger.log(Level.SEVERE, "Exception from database", e);
                }
            }
            this.stop();
        }).start();
    }

    public void stop(){
        stopFlag = true;
        isStarted = false;

        notifyStatusListeners();
    }

    public void doRandomQuery(List<Integer> usedId) throws DBException{
        int randomOperation = random.nextInt(5);

        switch(randomOperation){
            case 0 : {
                doFindAllQuery();
                break;
            }
            case 1 : {
                doFindByRandomIdQuery();
                break;
            }
            case 2 : {
                doSaveNewDataSetQuery();
                break;
            }
            case 3 : {
                doUpdateDataSetQuery();
                break;
            }
            case 4 : {
                doDeleteByRandomIdQuery();
                break;
            }
        }
    }

    public void doFindAllQuery(){
        try {
            dbService.findAll();
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }

    public void doFindByRandomIdQuery(){
        try {
            if (!usedId.isEmpty()) {
                int randomId = usedId.get(random.nextInt(usedId.size()));
                dbService.findById(Long.valueOf(randomId));
            }
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }

    public void doSaveNewDataSetQuery(){
        try {
            int randomId = 0;
            boolean uniqueId = false;
            while (!uniqueId) {
                randomId = random.nextInt(10000);
                if (!usedId.contains(randomId)) {
                    uniqueId = true;
                    usedId.add(Integer.valueOf(randomId));
                }
            }
            dbService.save(new UserDataSet(randomId, "simpleName", random.nextInt(100)));
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }

    public void doUpdateDataSetQuery(){
        try {
            if (!usedId.isEmpty()) {
                int randomId = usedId.get(random.nextInt(usedId.size()));
                UserDataSet randomUser = dbService.findById(Long.valueOf(randomId));
                randomUser.setName("changedName");
                dbService.update(randomUser);
            }
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }

    public void doDeleteByRandomIdQuery(){
        try {
            if (!usedId.isEmpty()) {
                int randomId = usedId.get(random.nextInt(usedId.size()));
                dbService.deleteById(Long.valueOf(randomId));
                usedId.remove(Integer.valueOf(randomId));
            }
        }catch(DBException e){
            logger.log(Level.SEVERE, "Exception from database", e);
        }
    }


    public int getCacheHit(){
        return dbService.getCacheHit();
    }

    public int getCacheMiss(){
        return dbService.getCacheMiss();
    }

    public void addCacheHitMissListener(CacheHitMissListener listener){
        dbService.addCacheHitMissListener(listener);
    }

    public void addQueryListener(QueryListener listener){
        dbService.addQueryListener(listener);
    }

    public void addQueryResultListener(QueryResultListener listener){
        dbService.addQueryResultListener(listener);
    }

    public void addGeneratorStatusListener(GeneratorStatusListener listener){
        statusListenerSet.add(listener);
    }

    private void notifyStatusListeners(){
        for(GeneratorStatusListener listener : statusListenerSet){
            listener.generatorStatusChanged();
        }
    }
}
