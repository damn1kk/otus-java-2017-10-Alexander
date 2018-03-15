package ru.otus.homework15.queryGenerator;

import ru.otus.homework15.MessageSystemContext;
import ru.otus.homework15.database.dataSet.DataSet;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.MessageSystem;
import ru.otus.homework15.messageSystem.messages.Message;
import ru.otus.homework15.messageSystem.messages.toDB.*;
import ru.otus.homework15.messageSystem.messages.toFrontend.MsgAnswerFromGenerator;
import ru.otus.homework15.utils.ReflectionUtils;
import ru.otus.homework15.utils.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryGeneratorImpl <T extends DataSet, ID extends Serializable> implements  QueryGeneratorService{
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Address address;
    private final MessageSystemContext messageSystemContext;

    private Class<T> type;
    private Set<GeneratorStatusListener> statusListenerSet = new HashSet<>();
    private static  volatile boolean stopFlag = false;
    private static  volatile boolean isStarted = false;
    private List<Integer> usedId = new LinkedList<>();
    private Random random = new Random();

    public QueryGeneratorImpl(Class<T> type, Address address, MessageSystemContext msContext){
        this.type = type;
        this.address = address;
        this.messageSystemContext = msContext;
        messageSystemContext.getMessageSystem().addAddressee(this);
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start(){
        if(!isStarted()) {
            stopFlag = false;
            isStarted = true;

            notifyStatusListeners();

            new Thread(() -> {
                while (!stopFlag) {
                    doRandomQuery();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                this.stop();
            }).start();
        }
    }

    public void stop(){
        stopFlag = true;
        isStarted = false;

        notifyStatusListeners();
    }

    public void doRandomQuery(){
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

    public void doFindAllQuery() {
        Message message = new MsgFindAll(getAddress(), messageSystemContext.getDbAddress());
        messageSystemContext.getMessageSystem().sendMessage(message);
    }

    public void doFindByRandomIdQuery(){
        if (!usedId.isEmpty()) {
            int randomId = usedId.get(random.nextInt(usedId.size()));
            Message message = new MsgFindById(getAddress(), messageSystemContext.getDbAddress(), randomId);
            messageSystemContext.getMessageSystem().sendMessage(message);
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
            T object = ReflectionUtils.newInstanceWithIdAndRandomPrimitiveParameters(type, randomId);
            Message message = new MsgSaveNewUserDataSet(getAddress(), messageSystemContext.getDbAddress(), object);
            messageSystemContext.getMessageSystem().sendMessage(message);
        }catch(Exception e){
            logger.log(Level.SEVERE, "Exception from init new object for save", e);
        }
    }

    public void doUpdateDataSetQuery(){
        try {
            if (!usedId.isEmpty()) {
                int randomId = usedId.get(random.nextInt(usedId.size()));
                String randomString = StringUtils.generateRandomString(20);
                Message message = new MsgUpdateDataSet(
                        getAddress(), messageSystemContext.getDbAddress(), Long.valueOf(randomId), randomString
                );
                messageSystemContext.getMessageSystem().sendMessage(message);
            }
        }catch(Exception e){
            logger.log(Level.SEVERE, "Exception from change string field", e);
        }
    }

    public void doDeleteByRandomIdQuery() {
        if (!usedId.isEmpty()) {
            int randomId = usedId.get(random.nextInt(usedId.size()));
            Message message = new MsgDeleteById(getAddress(), messageSystemContext.getDbAddress(), randomId);
            messageSystemContext.getMessageSystem().sendMessage(message);
            usedId.remove(Integer.valueOf(randomId));
        }
    }

    @Override
    public void sendStringResultToFrontendService(String result){
        getMS().sendMessage(new MsgAnswerFromGenerator(getAddress(), messageSystemContext.getFrontAddress(), result));
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }

    @Override
    public void addGeneratorStatusListener(GeneratorStatusListener listener){
        statusListenerSet.add(listener);
    }

    @Override
    public void deleteGeneratorStatusListener(GeneratorStatusListener listener){
        statusListenerSet.remove(listener);
    }

    private void notifyStatusListeners(){
        for(GeneratorStatusListener listener : statusListenerSet){
            listener.generatorStatusChanged(isStarted());
        }
    }
}
