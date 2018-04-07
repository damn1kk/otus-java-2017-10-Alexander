package ru.otus.homework16;

import ru.otus.homework16.addressee.TypeOfAddressee;
import ru.otus.homework16.msg.AllDbServicesMsg;
import ru.otus.homework16.msg.AnswerRegisterMsg;
import ru.otus.homework16.msg.Msg;
import ru.otus.homework16.msg.RegisterMsg;
import ru.otus.homework16.workers.MsgWorker;
import ru.otus.homework16.workers.SocketMsgWorker;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server{
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String SERVER_NAME = "Server";
    private static final int PORT = 9090;
    private static final int THREADS_NUMBER = 2;

    private volatile boolean isStarted = false;
    private final ExecutorService executor;

    private List<MsgWorker> unregisteredWorkersToClients;
    private Map<String, MsgWorker> registeredWorkersToClients;
    private Map<String, TypeOfAddressee> typesOfClients;

    public Server(){
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        registeredWorkersToClients = new ConcurrentHashMap<>();
        unregisteredWorkersToClients = new CopyOnWriteArrayList<>();
        typesOfClients = new ConcurrentHashMap<>();
    }

    public void start() throws IOException{
        logger.info("Server started");
        isStarted = true;
        executor.submit(this::handleMsg);
        executor.submit(this::listenSocket);
    }

    private void listenSocket(){
        try(ServerSocket server = new ServerSocket(PORT)){
            while(isStarted) {
                Socket socketServerSide = server.accept();
                logger.info("Client connected: " + socketServerSide);
                SocketMsgWorker workerForClient = new SocketMsgWorker(socketServerSide);
                workerForClient.init();
                unregisteredWorkersToClients.add(workerForClient);
            }
        }catch(IOException e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void handleMsg(){
        while(true){
            registeredWorkersToClients.forEach((key, worker) -> {
                Msg msg = worker.poll();
                while(msg != null){
                    logger.info("Receive message: " + msg);
                    redirectMessage(msg);
                    msg = worker.poll();
                }
            });

            unregisteredWorkersToClients.forEach((worker) -> {
                Msg msg = worker.poll();
                while(msg != null){
                    logger.info("Receive message: " + msg);
                    if(msg instanceof RegisterMsg){
                        registerNewClient(worker, msg);
                    }
                    msg = worker.poll();
                }
            });
        }
    }

    private void redirectMessage(Msg msg){
        String to = msg.getTo();
        MsgWorker worker = registeredWorkersToClients.get(to);
        worker.send(msg);
    }

    private void registerNewClient(MsgWorker worker, Msg msg){
        String requestedId = msg.getFrom();
        TypeOfAddressee type = ((RegisterMsg)msg).getTypeOfAddressee();

        while(registeredWorkersToClients.containsKey(requestedId)){
            requestedId = generateNewId(requestedId);
        }

        if (unregisteredWorkersToClients.contains(worker)) {
            unregisteredWorkersToClients.remove(worker);
        }
        if (registeredWorkersToClients.containsValue(worker)) {
            registeredWorkersToClients.remove(MapUtils.findFirstKeyByValue(registeredWorkersToClients, worker));
        }

        typesOfClients.put(requestedId, type);
        registeredWorkersToClients.put(requestedId, worker);
        worker.send(new AnswerRegisterMsg(SERVER_NAME, requestedId));
        if(type == TypeOfAddressee.DB_SERVICE){
            sendAllDBServicesIdsToAllFrontendServices();
        }
        if(type == TypeOfAddressee.FRONT_SERVICE){
            sendAllDbServicesToThisFrontendService(worker, requestedId);
        }
    }


    private void sendAllDbServicesToThisFrontendService(MsgWorker worker, String id){
        Set<String> allDBClients = getAllDBClients();
        worker.send(new AllDbServicesMsg(SERVER_NAME, id, allDBClients));
    }

    private void sendAllDBServicesIdsToAllFrontendServices(){
        Set<String> allDbClients = getAllDBClients();
        Set<String> allFrontendClients = getAllFrontendClients();
        for(String frontClientId : allFrontendClients){
            MsgWorker worker = registeredWorkersToClients.get(frontClientId);
            worker.send(new AllDbServicesMsg(SERVER_NAME, frontClientId, allDbClients));
        }
    }

    private Set<String> getAllFrontendClients(){
        final Set<String> clientsId = new HashSet<>();
        typesOfClients.forEach((id, type) ->{
            if(type == TypeOfAddressee.FRONT_SERVICE){
                clientsId.add(id);
            }
        });
        return clientsId;
    }

    private Set<String> getAllDBClients(){
        final Set<String> clientsId = new HashSet<>();
        typesOfClients.forEach((id, type) ->{
            if(type == TypeOfAddressee.DB_SERVICE){
                clientsId.add(id);
            }
        });
        return clientsId;
    }

    private String generateNewId(String id){
        Random random = new Random();
        id += random.nextInt(10);
        return id;
    }

    public void stop(){
        logger.info("Server stopped");
        isStarted = false;
        executor.shutdown();
    }

    public Set<String> getAllRegisteredClientsIdMsgSystem(){
        return registeredWorkersToClients.keySet();
    }
    public Collection<MsgWorker> getAllRegisteredWorkersMsgSystem(){
        return registeredWorkersToClients.values();
    }

    public void stopAllClients(){
        registeredWorkersToClients.forEach((key, worker) ->{
            try {
                worker.close();
            }catch(IOException e){
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
        registeredWorkersToClients.clear();

        unregisteredWorkersToClients.forEach((worker) ->{
            try{
                worker.close();
            }catch(IOException e){
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
        unregisteredWorkersToClients.clear();
    }
}
