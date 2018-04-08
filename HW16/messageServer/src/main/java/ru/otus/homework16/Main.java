package ru.otus.homework16;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
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
                    }else if(command.toLowerCase().startsWith("start dbservice") || command.toLowerCase().startsWith("start db")){
                        String[] commandAsArray = command.split(" ");
                        String processName = commandAsArray[2];
                        processManager.startDBService(processName);
                    }else if(command.toLowerCase().equals("start frontendservice") || command.toLowerCase().equals("start front")){
                        processManager.startFrontendService();
                    }else if(command.toLowerCase().startsWith("start frontendservice") || command.toLowerCase().startsWith("start front")){
                        String[] commandAsArray = command.split(" ");
                        String portNumber = commandAsArray[2];
                        processManager.startFrontendService(Integer.parseInt(portNumber));
                    }else if(command.toLowerCase().equals("start all")) {
                        processManager.startDBService();
                        processManager.startFrontendService();
                    }else if(command.toLowerCase().equals("stop all")){
                        server.stopAllClients();
                        processManager.stopAllProcess();
                    }else if(command.toLowerCase().startsWith("stop")){
                        String processName = command.substring(4).trim();
                        processManager.stopProcess(processName);
                    }else if(command.toLowerCase().equals("exit") || command.toLowerCase().equals("quite")){
                        server.stopAllClients();
                        server.stop();
                        processManager.stopAllProcess();
                        System.exit(0);
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        });
    }
}
