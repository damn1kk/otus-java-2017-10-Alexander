package ru.otus.homework04.gclogger;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Logger;

public class PeriodicGCLogger extends TimerTask {
    private static final Logger log = Logger.getLogger(PeriodicGCLogger.class.getName());
    private List<GarbageCollectorMXBean> gcBeanList = ManagementFactory.getGarbageCollectorMXBeans();

    @Override
    public void run() {
        StringBuilder strForLog = new StringBuilder();
        strForLog.append("\n============================");
        for(GarbageCollectorMXBean gcBean : gcBeanList){
            strForLog.append("\n");
            strForLog.append("GC name: ").append(gcBean.getName()).append("\n");
            strForLog.append("Total collections: ").append(gcBean.getCollectionCount()).append("\n");
            strForLog.append("Total time for collections: ").append(gcBean.getCollectionTime()).append("ms").append("\n");
        }
        strForLog.append("============================");
        log.warning(strForLog.toString());
    }

    public void run(String additionalString) {
        StringBuilder strForLog = new StringBuilder();
        strForLog.append("\n");
        strForLog.append(additionalString).append("\n");
        for(GarbageCollectorMXBean gcBean : gcBeanList){
            strForLog.append("GC name: ").append(gcBean.getName()).append("\n");
            strForLog.append("Total collections: ").append(gcBean.getCollectionCount()).append("\n");
            strForLog.append("Total time for collections: ").append(gcBean.getCollectionTime()).append("ms").append("\n");
        }
        log.warning(strForLog.toString());
    }
}
