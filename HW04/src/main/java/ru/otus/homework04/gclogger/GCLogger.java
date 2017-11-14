package ru.otus.homework04.gclogger;

import javax.management.MBeanServer;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Timer;

public class GCLogger {
    public GCLogger(){
    }

    public void addPeriodicGCLogger(int periodForGCLoggerInSeconds){
        Timer timer = new Timer();
        timer.schedule(new PeriodicGCLogger(), 0, periodForGCLoggerInSeconds * 1000);
    }

    public void addNotificationGCToLog() throws Exception{
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        List<GarbageCollectorMXBean> gcBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        GCListener gcListener = new GCListener();
        mbs.addNotificationListener(gcBeanList.get(0).getObjectName(), gcListener, null, null);
        mbs.addNotificationListener(gcBeanList.get(1).getObjectName(), gcListener, null, null);
    }
}
