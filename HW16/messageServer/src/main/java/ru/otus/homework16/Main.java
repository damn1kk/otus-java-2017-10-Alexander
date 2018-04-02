package ru.otus.homework16;

import ru.otus.homework16.processRunner.ProcessRunner;
import ru.otus.homework16.processRunner.ProcessRunnerImpl;
import ru.otus.homework16.workers.MsgWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getClass().getName());

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();
        ProcessManager processManager = new ProcessManager();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()->{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                try {
                    String command = reader.readLine();

                    if (command.toLowerCase().equals("show clients") || command.toLowerCase().equals("show")) {
                        Set<String> clients = server.getAllRegisteredClientsIdMsgSystem();
                        System.out.println("Total clients: " + clients.size());
                        for (String c : clients) {
                            System.out.println(c);
                        }
                    }else if(command.toLowerCase().equals("start dbservice") || command.toLowerCase().equals("start db")){
                        processManager.startDBService();
                    }else if(command.toLowerCase().equals("start frontendservice") || command.toLowerCase().equals("start front")){
                        processManager.startFrontendService();
                    }else if(command.toLowerCase().equals("start all")) {
                        processManager.startDBService();
                        processManager.startFrontendService();
                    }else if(command.toLowerCase().equals("stop all")){
                        server.stopAllClients();
                        processManager.stopAllProcess();
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        });
    }
}
