package ru.otus.homework12.database;

import ru.otus.homework12.database.dataSet.UserDataSet;
import ru.otus.homework12.database.dbService.DBException;
import ru.otus.homework12.database.dbService.DBService;
import ru.otus.homework12.database.dbService.hibernateService.HibernateCachedUserDBService;

import java.util.*;

public class QueryGenerator implements Runnable{
    private static volatile boolean stopFlag = false;
    HibernateCachedUserDBService dbService = new HibernateCachedUserDBService();
    private static QueryGenerator queryGenerator;
    private static volatile boolean isStarted = false;

    private QueryGenerator(){}

    public static QueryGenerator instance(){
        if(queryGenerator == null){
            queryGenerator = new QueryGenerator();
        }
        return queryGenerator;
    }

    public void run(){
        stopFlag = false;
        isStarted = true;
        List<Integer> usedId = new LinkedList<>();
        while(!stopFlag) {
            try {
                doRandomOperation(usedId);
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException ex){
                }
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        stopFlag = true;
        isStarted = false;

    }

    private void doRandomOperation(List<Integer> usedId) throws DBException{
        Random random = new Random();
        int randomOperation = random.nextInt(5);

        switch(randomOperation){
            case 0 : {
                System.out.println("Make operation: FIND ALL");
                dbService.findAll();
                break;
            }

            case 1 : {
                if(!usedId.isEmpty()) {
                    int randomId = usedId.get(random.nextInt(usedId.size()));
                    System.out.println("Make operation: FIND BY ID");
                    dbService.findById(Long.valueOf(randomId));
                }
                break;
            }

            case 2 : {
                int randomId = 0;
                boolean uniqueId = false;
                while(!uniqueId){
                    randomId = random.nextInt(10000);
                    if(!usedId.contains(randomId)){
                        uniqueId = true;
                        usedId.add(Integer.valueOf(randomId));
                    }
                }
                System.out.println("Make operation: SAVE");
                dbService.save(new UserDataSet(randomId,"simpleName", random.nextInt(100)));
                break;
            }

            case 3 : {
                if(!usedId.isEmpty()) {
                    int randomId = usedId.get(random.nextInt(usedId.size()));
                    UserDataSet randomUser = dbService.findById(Long.valueOf(randomId));
                    randomUser.setName("changedName");
                    System.out.println("Make operation: UPDATE");
                    dbService.update(randomUser);
                }
                break;
            }

            case 4 : {
                if(!usedId.isEmpty()) {
                    int randomId = usedId.get(random.nextInt(usedId.size()));
                    System.out.println("Make operation: DELETE BY ID");
                    dbService.deleteById(Long.valueOf(randomId));
                    usedId.remove(Integer.valueOf(randomId));
                }
                break;
            }
        }

    }

    public int getCacheHit(){
        return dbService.getCacheHit();
    }

    public int getCacheMiss(){
        return dbService.getCacheMiss();
    }

    public boolean isStarted() {
        return isStarted;
    }
}
