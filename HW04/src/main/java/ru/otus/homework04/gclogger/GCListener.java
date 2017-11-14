package ru.otus.homework04.gclogger;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import java.util.logging.Logger;

public class GCListener implements NotificationListener {
    private static final Logger log = Logger.getLogger(GCListener.class.getName());

    @Override
    public void handleNotification(Notification notification, Object handback) {
        StringBuilder stringForLog = new StringBuilder();

        GarbageCollectionNotificationInfo gcNotificationInfo =
                GarbageCollectionNotificationInfo.from(
                        (javax.management.openmbean.CompositeData) notification.getUserData()
                );

        String gcName = gcNotificationInfo.getGcName();
        stringForLog.append("\n");
        stringForLog.append("GC NAME: ").append(gcName).append(";");

        String gcGeneration;
        if(gcNotificationInfo.getGcAction().equals("end of major GC")){
            gcGeneration = "old";
        } else if(gcNotificationInfo.getGcAction().equals("end of minor GC")){
            gcGeneration = "young";
        } else{
            gcGeneration = "unknown generation";
        }
        stringForLog.append(" ").append("GC GENERATION: ").append(gcGeneration).append(";");

        long gcDuration = gcNotificationInfo.getGcInfo().getDuration();
        stringForLog.append(" ").append("GC DURATION: ").append(gcDuration).append("ms;");
        stringForLog.append("\n");

        log.info(stringForLog.toString());
    }
}
