package ru.otus.homework04;

import ru.otus.homework04.gclogger.GCLogger;
import ru.otus.homework04.gclogger.PeriodicGCLogger;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.LogManager;

public class Main {
    private static final int PERIOD_FOR_LOG_GC_IN_SECONDS = 30;

    public static void main(String[] args) throws Exception{
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("Starting pid: " + pid);

        try{
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        }catch(IOException ex){
            System.err.println("Could not setup logger configuration " + ex.toString());
        }

        GCLogger gcLogger = new GCLogger();
        gcLogger.addNotificationGCToLog();
        gcLogger.addPeriodicGCLogger(PERIOD_FOR_LOG_GC_IN_SECONDS);

        MemoryLeaker leaker = new MemoryLeaker();
        try {
            leaker.leak();
        }catch(OutOfMemoryError error){
            leaker.clear();
            long timeAfterStart = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
            StringBuilder additionalString = new StringBuilder();
            additionalString.append("---------------------------------\n");
            additionalString.append("FINAL STATISTIC: \n");
            additionalString.append("The app worked: ").append(timeAfterStart).append(" seconds");

            new PeriodicGCLogger().run(additionalString.toString());
            System.exit(2);
        }



    }
}
