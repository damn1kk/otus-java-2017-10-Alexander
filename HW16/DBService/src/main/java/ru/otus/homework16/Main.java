package ru.otus.homework16;

import java.util.logging.Logger;

public class Main {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String HOST = "localhost";
    private static final int PORT = 9090;
    private static final String DEFAULT_NAME = "DBService";

    public static void main(String[] args) {
        if(args.length != 0){
            String processName = args[0];
            new DBServiceMsgClient(processName).connectToServerBySocket(HOST, PORT);
        }else {
            new DBServiceMsgClient(DEFAULT_NAME).connectToServerBySocket(HOST, PORT);
        }
    }




}
