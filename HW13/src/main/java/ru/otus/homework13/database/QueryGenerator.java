package ru.otus.homework13.database;

import ru.otus.homework13.database.dataSet.UserDataSet;
import ru.otus.homework13.database.dbService.DBException;
import ru.otus.homework13.database.dbService.hibernateService.HibernateCachedUserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QueryGenerator implements Runnable{
    private static volatile boolean stopFlag = false;
    private static volatile boolean isStarted = false;

    HibernateCachedUserService dbService;

    private QueryGenerator(HibernateCachedUserService dbService){
        this.dbService = dbService;
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
