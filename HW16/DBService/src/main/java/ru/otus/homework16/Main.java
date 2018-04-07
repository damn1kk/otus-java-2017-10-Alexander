package ru.otus.homework16;

import java.util.logging.Logger;

public class Main {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) {
        new DBServiceMsgClient("DBService").connectToServerBySocket(HOST, PORT);
    }




}
