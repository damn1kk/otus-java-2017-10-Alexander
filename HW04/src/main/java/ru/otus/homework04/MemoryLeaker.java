package ru.otus.homework04;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class MemoryLeaker{
    List<String> stringList;
    List<GarbageCollectorMXBean> gcBeanList;

    private static final int MEGABYTE = 1024* 1024;

    public MemoryLeaker() {
        stringList = new ArrayList<>();
        gcBeanList = ManagementFactory.getGarbageCollectorMXBeans();
    }

    void leak() {
        while (true) {
            for (int i = 0; i < 10_000; i++) {
                stringList.add(new String("xz"));
            }

            for (int i = 0; i < 1000; i++) {
                stringList.remove(0);
            }
        }
    }

    void clear(){
        stringList.clear();
    }


}
